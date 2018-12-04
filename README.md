[![Build Status](https://travis-ci.com/mtumilowicz/junit5-nested-test.svg?branch=master)](https://travis-ci.com/mtumilowicz/junit5-nested-test)

# junit5-nested-test
Example of using nested tests in Junit5.

_Reference_: https://junit.org/junit5/docs/current/user-guide/#writing-tests-nested

# preface
Nested tests give the test writer more capabilities to 
express the relationship among several group of tests.

* Only non-static nested classes (i.e. inner classes) can 
serve as `@Nested` test classes.
* Nesting can be arbitrarily deep
* `@BeforeAll` and `@AfterAll` methods cannot be defined in
an `@Nested` class - Java does not allow static members 
in inner classes.

# project description
It is a good practice to group tests of one method in
an inner class:
1. suppose we want to test:
    ```
    class MathGroup {
        OptionalDouble average(Collection<Integer> ints) {
            if (isNull(ints)) {
                throw new IllegalArgumentException("collection cannot be null");
            }
    
            return ints.stream()
                    .filter(Objects::nonNull)
                    .mapToInt(x -> x)
                    .average();
        }
    }
    ```
1. method `average(Collection<Integer> ints)`
    * throws `IllegalArgumentException` if input is null
    * ignore nulls in non null collection
    * return `OptionalDouble`
1. we group tests in the inner class
    ```
     class NestedTests {
         
        // tests of other methods...
         
        @Nested
        @DisplayName("tests of average(Collection<Integer> ints)")
        class Average_collection {
            
            MathGroup mathGroup = new MathGroup();
            
            @Test
            void nullCollection() {
                assertThrows(IllegalArgumentException.class, () -> mathGroup.average(null));
            }
            
            @Test
            void emptyCollection() {
                assertTrue(mathGroup.average(Collections.emptyList()).isEmpty());
            }
            
            @Test
            @DisplayName("null elements are ignored")
            void collectionWithNullElement() {
                assertThat(mathGroup.average(Arrays.asList(1, null)), is(OptionalDouble.of(1)));
            }
            
            @Test
            void wellFormattedInput() {
                assertThat(mathGroup.average(Arrays.asList(1, 2, 3, 11)), is(OptionalDouble.of(4.25)));
            }
        }
        
        // tests of other methods...
    }
    ```
1. reports are well formatted:
    ```
    Test Results
        NestedTests
            tests of average(Collection<Integer> ints)
                wellFormattedInput()
                collectionWithNullElement()
                emptyCollection()
                nullCollection()
    ```