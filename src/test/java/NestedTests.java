import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.OptionalDouble;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by mtumilowicz on 2018-12-04.
 */
 class NestedTests {
     
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
    
}
