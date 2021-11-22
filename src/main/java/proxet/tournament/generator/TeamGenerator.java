package proxet.tournament.generator;

import proxet.tournament.generator.dto.Player;
import proxet.tournament.generator.dto.TeamGeneratorResult;

import java.io.*;
import java.util.*;

public class TeamGenerator {

    public TeamGeneratorResult generateTeams(String filePath) {
        List<Player> team1 = null;
        List<Player> team2 = null;

        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] playerData;
            TreeMap<Integer, Player> playerTreeMap = new TreeMap<>(Collections.reverseOrder());

            //change for the whole file
//            for (int i = 0; i < 18; i++) {
            while ((line = br.readLine()) != null) {

                //split line to get 3 parameters separately
                playerData = line.split("\t");

                //get players data
                String nickname = playerData[0];
                int t = Integer.parseInt(playerData[1]);
                int vehicle = Integer.parseInt(playerData[2]);

                //put players into sorted map, where waiting time is a key and player is a value
                playerTreeMap.put(t, new Player(nickname, vehicle));
//                System.out.println(Arrays.toString(playerData));

            }
            //players with the biggest waiting time
            List<Player> lobbyPlayers = new ArrayList<>();
            //additional list to contain vehicle types
            List<Integer> tmp = new ArrayList<>();
            for (Map.Entry<Integer, Player> entry : playerTreeMap.entrySet()) {
                System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue().getNickname() + " " + entry.getValue().getVehicleType());

                //occurrences of some vehicle type in our lobby
                int occurrences = 0;
                if (lobbyPlayers.size() != 18) {

                    if (entry.getValue().getVehicleType() == 1) {
                        occurrences = Collections.frequency(tmp, entry.getValue().getVehicleType());

                        if (occurrences < 6) {
                            lobbyPlayers.add(entry.getValue());
                            tmp.add(entry.getValue().getVehicleType());
                        }
                    } else if (entry.getValue().getVehicleType() == 2) {
                        occurrences = Collections.frequency(tmp, entry.getValue().getVehicleType());

                        if (occurrences < 6) {
                            lobbyPlayers.add(entry.getValue());
                            tmp.add(entry.getValue().getVehicleType());
                        }
                    } else if (entry.getValue().getVehicleType() == 3) {
                        occurrences = Collections.frequency(tmp, entry.getValue().getVehicleType());

                        if (occurrences < 6) {
                            lobbyPlayers.add(entry.getValue());
                            tmp.add(entry.getValue().getVehicleType());
                        }
                    }

                } else {
                    break;
                }

            }


            for (Player pl : lobbyPlayers) {
                System.out.println(pl.getNickname() + " " + pl.getVehicleType());
            }

//            for (int i = 0; i < 18; i++) {
//                Player player = lobbyPlayers.get(i);
//                if (tmp.size()!=0 && team2.size()!=9){
//                    if (tmp.contains(player.getVehicleType())){
//                        team2.add(player);
//                        tmp.add(player.getVehicleType());
//                    }
//                }
//                team1.add(player);
//                tmp.add(player.getVehicleType())
//            }

        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TeamGeneratorResult(team1, team2);
    }


}
