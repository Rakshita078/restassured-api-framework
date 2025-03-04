package concepts;

public class Hamcrest {

}
/*-> Hamcrest is a well known assertion library used for unit testing along with JUnit.
-> Hamcrest can be used along with Rest Assured for assertions.
-> Uses matcher classes for making assertions

Adv:
-> Human readable and in plain english
-> Code is neat and intuitive
-> Provides thin methods like "is" and "not", also called as decorators, for more readibility

Hamcrest Vs TestNG
-> Readibility
-> Descriptive error messages
-> Type Safety

Collection matchers (List, Array, Map, etc.)
==============================================
hasItem() -> check single element in a collection
not(hasItem()) -> check single element is NOT in a collection
hasItems() -> Check all elements are in a collection
contains() -> Check all elements are in a collection and in a strict order
containsInAnyOrder() -> Check all elements are in a collection and in any order
empty() -> Check if collection is empty
not(emptyArray()) -> Check if the Array is not empty
hasSize() -> Check size of a collection
everyItem(startsWith()) -> Check if every item in a collection starts with specified string

hasKey() -> Map -> Check if Map has the specified key [value is not checked]
hasValue() -> Map -> Check if Map has at least one key matching specified value
hasEntry() -> Maps -> Check if Map has the specified key value pair
equalTo(Collections.EMPTY_MAP) -> Maps [Check if empty]
can be used only on Strings:
allOf() -> Matches if all matchers matches
anyOf() -> Matches if any of the matchers matches

Numbers:
greaterThanOrEqualTo()
lessThan()
lessThanOrEqualTo()

String:
containsString()
emptyString()*/