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


            //change for the whole file
//            for (int i = 0; i < 18; i++) {
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
            Map<Player, Integer> sortedMap = sortByComparator(map, false);
            //players with the biggest waiting time
            List<Player> lobbyPlayers = new ArrayList<>();

            //additional list to contain vehicle types in our lobby
            List<Integer> tmp = new ArrayList<>();

            //map of players in lobby (18 players);
            Map<Player, Integer> lobbyMap = new HashMap<Player, Integer>();

            for (Map.Entry<Player, Integer> entry : sortedMap.entrySet()) {


                //occurrences of some vehicle type in our lobby
                int occurrences = 0;
                if (lobbyPlayers.size() != 18) {

                    // check occurrences of every vehicle, it should be 6 for each type
                    if (entry.getKey().getVehicleType() == 1) {
                        occurrences = Collections.frequency(tmp, entry.getKey().getVehicleType());

                        if (occurrences < 6) {
                            lobbyPlayers.add(entry.getKey());
                            tmp.add(entry.getKey().getVehicleType());
                            lobbyMap.put(entry.getKey(), entry.getKey().getVehicleType());
                        }
                    } else if (entry.getKey().getVehicleType() == 2) {
                        occurrences = Collections.frequency(tmp, entry.getKey().getVehicleType());

                        if (occurrences < 6) {
                            lobbyPlayers.add(entry.getKey());
                            tmp.add(entry.getKey().getVehicleType());
                            lobbyMap.put(entry.getKey(), entry.getKey().getVehicleType());
                        }
                    } else if (entry.getKey().getVehicleType() == 3) {
                        occurrences = Collections.frequency(tmp, entry.getKey().getVehicleType());

                        if (occurrences < 6) {
                            lobbyPlayers.add(entry.getKey());
                            tmp.add(entry.getKey().getVehicleType());
                            lobbyMap.put(entry.getKey(), entry.getKey().getVehicleType());
                        }
                    }

                } else {
                    break;
                }

            }
            sortedMap = sortByComparator(lobbyMap, true);

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

            System.out.println("Teams 1 : ");
            for (Player pl: team1) {
                System.out.println(pl.getNickname() + " " + pl.getVehicleType());
            }
            System.out.println("Teams 2 : ");
            for (Player pl: team2) {
                System.out.println(pl.getNickname() + " " + pl.getVehicleType());
            }

//            for (Map.Entry<Player,Integer> entry : sortedMap.entrySet()) {
//                System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue());
//
//                if (counter1!=3) {
//                    team1.add(entry.getKey());
//                    counter1++;
//                    counter2 = 0;
//                }
//                if(counter2!=3){
//                    team2.add(entry.getKey());
//
//                }
//            }


        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TeamGeneratorResult(team1, team2);
    }


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

