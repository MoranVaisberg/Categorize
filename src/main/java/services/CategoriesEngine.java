package services;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class implements an algorithm to find categories and the positions in the phrase
 * Step1: Pick a String from an input categories (a primary expression) and find a match or prefix in the dictionary categories
 *   Match: e.g. category = 'Sales', dictionary = 'Sales'
 *   Prefix: e.g. category = 'Vice', dictionary = 'Vice President'
 *
 * Step2: If found a match, add the category and the position to a map
 * Step3: If found a prefix, concatenate next String to the prefix
 *   Prefix: e.g. category = 'Vice', dictionary = 'Vice President', next String = 'President'
 *                concatenated String = 'Vice President'
 *                Proceed to Step2
 *
 * Step4: During Step2 and Step3, perform the same action for secondary expression.
 *   Primary Expression can be a concatenation of several input categories, while Secondary Expression is always single category
 *   e.g. Primary Expression = 'Vice Manager', Secondary Expression = "Manager"
 *   dictionary = {'Vice President', 'Manager Department'}
 *
 *   In case Primary returns false, proceed with Secondary on the same loop iteration.
 *        if match, proceed to Step2 and so on.
 *
 * Step5: No Match - proceed to Step1 to pick next String
 */
public class CategoriesEngine {

    private static final String SPACE_DELIMETER = " ";

    private enum State {
        PRIMARY_FOUND,
        PRIMARY_PREFIX_FOUND,
        SECONDARY_FOUND,
        SECONDARY_PREFIX_FOUND,
        NOT_FOUND;
    }

    /**
     * Generates a result of categories found in dictionary and their positions
     * @param dictionary - Seeded dictionary
     * @param phrase - String, contains categories, e.g. "Vice President of Sales and Marketing"
     * @return
     */
    public Map<String, Integer> getCategoriesAndPositions(Set<String> dictionary, String phrase) {
        Map<String, Integer> categoriesPosition = new TreeMap<String, Integer>();
        String[] categories =  getCategories(phrase);
        StringBuilder primaryExpression = new StringBuilder();

        for ( int i=0; i< categories.length; i++ ){
            String secondaryExpression = categories[i];
            primaryExpression.append(secondaryExpression);
            findCategoryInDictionary(dictionary, primaryExpression, secondaryExpression, categoriesPosition, phrase);
        }

        return categoriesPosition;
    }

    /**
     * Look for a exact match or prefix match in the dictionary categories
     */
    private void findCategoryInDictionary(Set<String> dictionary,
                                          StringBuilder primaryExpression,
                                          String secondaryExpression,
                                          Map<String, Integer> categoriesPosition,
                                          String phrase) {

        State state = getState(dictionary, primaryExpression.toString(), secondaryExpression);

        switch (state) {
            case PRIMARY_FOUND:
                    categoriesPosition.put(primaryExpression.toString(), phrase.indexOf(primaryExpression.toString()));
                    primaryExpression.setLength(0);
                    break;
            case PRIMARY_PREFIX_FOUND:
                    primaryExpression.append(SPACE_DELIMETER);
                    break;
            case SECONDARY_FOUND:
                    categoriesPosition.put(secondaryExpression, phrase.indexOf(secondaryExpression));
                    primaryExpression.setLength(0);
                    break;
            case SECONDARY_PREFIX_FOUND:
                    primaryExpression.setLength(0);
                    primaryExpression.append(secondaryExpression);
                    primaryExpression.append(SPACE_DELIMETER);
                    break;
            default:
                // String is not in dictionary
                // clean the primaryExpression only if no match, otherwise preserve it to next iteration
                    primaryExpression.setLength(0);
                    break;
            }
    }

    /**
     *  Find a match or prefix in the dictionary categories for primaryExpression and secondaryExpression
     *  If primary found (prefix or exact category), stop searching in the dictionary
     *  If secondary found (prefix or exact category), proceed till end of dictionary or primary found
     */
    private State getState(Set<String> dictionary, String primaryExpression, String secondaryExpression) {
        State state = State.NOT_FOUND;

        for (String category : dictionary){
            // match found
            if(category.equals(primaryExpression)){
                state = State.PRIMARY_FOUND;
                break;
            }
            // prefix match found
            if(category.startsWith(primaryExpression)) {
                state = State.PRIMARY_PREFIX_FOUND;
                break;
            }
            //match found
            if(category.equals(secondaryExpression)){
                state = State.SECONDARY_FOUND;
            }
            // prefix match found
            if(category.startsWith(secondaryExpression)) {
                state = (state.equals(State.NOT_FOUND)) ? State.SECONDARY_PREFIX_FOUND : state;
            }
        }
        return state;
    }

    private String[] getCategories(String phrase) {
        return phrase.split(SPACE_DELIMETER);
    }


}
