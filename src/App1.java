import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class App1 {

    public static void main(String[] args) {

        var p1 = new Player("P1", 0, Position.GOALKEEPER);
        var p2 = new Player("P2", 0,  Position.GOALKEEPER);
        var p3 = new Player("P3", 1, Position.DEFENDER);
        var p4 = new Player("P4", 2, Position.DEFENDER);
        var p5 = new Player("P5", 1, Position.DEFENDER);
        var p6 = new Player("P6", 3, Position.DEFENDER);
        var p7 = new Player("P7", 4, Position.MIDFIELDER);
        var p8 = new Player("P8", 12, Position.MIDFIELDER);
        var p9 = new Player("P9", 12, Position.STRIKER);
        var p10 = new Player("P10", 7, Position.STRIKER);

        var t1 = new Team("T1", Set.of(p1, p3, p4, p7, p9));
        var t2 = new Team("T2", Set.of(p2, p5, p6, p8, p10));

        var teams = List.of(t1, t2);

        // Zadanie 1
        // Dla każdej drużyny wyznacz listę zawodników o największej ilości bramek.
        // Wykorzystamy map
        var topScorers = teams
                .stream()
                // Konwersja JEDEN DO JEDNEGO
                // Z JEDNEGO obiektu typu Team przechodzisz na JEDNA listę players o największej
                // ilości zdobytych bramek
                // Team ---> List<Player>
                .map(Team::topGoalScorers)
                .toList();
        /*System.out.println("==> (1)");
        topScorers.forEach(System.out::println);*/

        var teamsTopScorers = teams
                .stream()
                .collect(toMap(identity(), Team::topGoalScorers));
        /*System.out.println("==> (2)");
        teamsTopScorers.forEach((team, players) -> System.out.println(team.name() + " => " + players));*/

        // Zadanie 2
        // Spośród zawodników wszystkich drużyn, wyznacz tego, który zdobył najwięcej goli
        // Wykorzystamy flatMap

        /*
            JEDEN DO 0 ... n
            T1 ==> [P1, P3, P4, P7, P9]
            T2 ==> [P2, P5, P6, P8, P10]
            [P1, P3, P4, P7, P9, P2, P5, P6, P8, P10]
        */

        var bestScorers = teams
                .stream()
                .flatMap(team -> team.players().stream())
                .collect(groupingBy(Player::goals))
                .entrySet()
                .stream()
                .max(comparingInt(Map.Entry::getKey))
                .orElseThrow()
                .getValue();
        System.out.println("==> (3)");
        System.out.println(bestScorers);
    }
}
