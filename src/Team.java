import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;

public record Team (String name, Set<Player> players) {
    public List<Player> topGoalScorers() {
        return players
                .stream()
                // Grupowanie po liczbie zdobytych bramek
                .collect(groupingBy(Player::goals))
                // Przejście z mapy na strumień
                .entrySet()
                .stream()
                // Wyznaczenie pary o największym kluczu - największej ilości zdobytych bramek
                .max(comparingInt(Map.Entry::getKey))
                .orElseThrow()
                // Pobranie listy players, którzy zdobyli najwięcej bramek
                .getValue();
    }
}
