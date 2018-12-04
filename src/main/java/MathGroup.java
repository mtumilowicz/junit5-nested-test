import java.util.Collection;
import java.util.Objects;
import java.util.OptionalDouble;

import static java.util.Objects.isNull;

public class MathGroup {
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