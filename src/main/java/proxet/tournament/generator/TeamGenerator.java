package proxet.tournament.generator;

import proxet.tournament.generator.dto.Player;
import proxet.tournament.generator.dto.TeamGeneratorResult;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class TeamGenerator {

    public TeamGeneratorResult generateTeams(String filePath) {
        List<Player> team1 = new LinkedList<>();
        List<Player> team2 = new LinkedList<>();

        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] playerData;
            Map<Player, Integer> map = new HashMap<Player, Integer>();

            //read file
            while ((line = br.readLine()) != null) {

                //split line to get 3 parameters separately
                playerData = line.split("\\s+");

                //get players data
                String nickname = playerData[0];
                int t = Integer.parseInt(playerData[1]);
                int vehicle = Integer.parseInt(playerData[2]);

                //put players into sorted map, where waiting time is a key and player is a value
                map.put(new Player(nickname, vehicle), t);

            }
            //create map sorted by players waiting time in descending order
            Map<Player, Integer> sortedMap = sortByComparator(map, false);


            //additional list to contain vehicle types in our lobby
            List<Integer> tmp = new ArrayList<>();

            //map of players in lobby (18 players);
            Map<Player, Integer> lobbyMap = new HashMap<Player, Integer>();

            for (Map.Entry<Player, Integer> entry : sortedMap.entrySet()) {


                //occurrences of some vehicle type in our lobby
                int occurrences;
                if (lobbyMap.size() != 18) {

                    // check occurrences of every vehicle, it should be 6 in lobby
                    occurrences = Collections.frequency(tmp, entry.getKey().getVehicleType());

                    //if vehicle type occurred less or equal than 6 times we can add player with this vehicle
                    if (occurrences < 6) {
                        tmp.add(entry.getKey().getVehicleType());
                        lobbyMap.put(entry.getKey(), entry.getKey().getVehicleType());
                    }

                } else {
                    break;
                }

            }
            //sorted map with 18 players
            sortedMap = sortByComparator(lobbyMap, true);

            //distribute players in two teams
            int counter = 0;
            for (Map.Entry<Player, Integer> entry : sortedMap.entrySet())
            {
                if (counter%2==0){
                    team1.add(entry.getKey());
                }else{
                    team2.add(entry.getKey());
                }
                counter++;
            }


        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TeamGeneratorResult(team1, team2);
    }

    //additional method to sort map by value
    private static Map<Player, Integer> sortByComparator(Map<Player, Integer> unsortMap, final boolean order) {

        List<Entry<Player, Integer>> list = new LinkedList<Entry<Player, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Player, Integer>>() {
            public int compare(Entry<Player, Integer> o1,
                               Entry<Player, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<Player, Integer> sortedMap = new LinkedHashMap<Player, Integer>();
        for (Entry<Player, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}

