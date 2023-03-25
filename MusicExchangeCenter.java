import java.util.*;

public class MusicExchangeCenter {
    private List<User> users;
    private HashMap<String, Float> royalties;
    private List<Song> downloadedSongs;
    public MusicExchangeCenter(){
        users = new ArrayList<User>();
        royalties = new HashMap<>();
        downloadedSongs = new ArrayList<>();
    }

    public List onlineUsers(){
        ArrayList<User> online;
        online = new ArrayList();

        for(int i = 0; i < users.size(); i++){
            User obj = users.get(i);
            if(obj.isOnline() == true){
                online.add(obj);
            }
        }
        return online;
    }

    public List allAvailableSongs(){
        ArrayList<Song> songs;
        List<User> u;
        songs = new ArrayList();
        u = onlineUsers();

        for (User obj : u) {
            List<Song> s = obj.getSongList();
            for (int j = 0; j < s.size(); j++) {
                songs.add(s.get(j));
            }
        }
        return songs;
    }

    public String toString() {
        int num_users = onlineUsers().size();
        int num_songs = allAvailableSongs().size();
        return "Music Exchange Center (" + num_users + " users online, " + num_songs + " songs available)";
    }
    public User userWithName(String s){
        for (User user : users) {
            String obj = user.getUserName();
            if (obj.equals(s)) {
                return user;
            }
        }
        return null;
    }
    public void registerUser(User x){
        if(users.size() == 0){
            users.add(x);
        }else{
            if(userWithName(x.getUserName()) == null){
                users.add(x);
            }
        }

    }
    public Song getSong(String title, String ownerName){
        int i = 0;
        int j = 0;
        float value = 0;
        while(i < users.size()){
            if(users.get(i).getUserName().equals(ownerName)){
                while(j < users.get(i).getSongList().size()){
                    if(users.get(i).getSongList().get(j).getTitle().equals(title)){
                        if(royalties == null){
                            royalties.put(users.get(i).getSongList().get(j).getArtist(), 0f);
                            downloadedSongs.add(users.get(i).getSongList().get(j));
                        }else{
                            if(royalties.containsKey(users.get(i).getSongList().get(j).getArtist())){
                                value = royalties.get(users.get(i).getSongList().get(j).getArtist());
                                royalties.put(users.get(i).getSongList().get(j).getArtist(), value + 0.25f);
                                downloadedSongs.add(users.get(i).getSongList().get(j));
                            }else{
                                royalties.put(users.get(i).getSongList().get(j).getArtist(), 0.25f);
                                downloadedSongs.add(users.get(i).getSongList().get(j));
                            }
                        }
                        return users.get(i).getSongList().get(j);
                    }
                    j++;
                }
            }
            j = 0;
            i++;
        }
        return null;
    }

    public List availableSongsByArtist(String artist){
        ArrayList songs;
        List<User> u;
        songs = new ArrayList();
        u = onlineUsers();
        Song temp;

        for(int i = 0; i < u.size(); i++){
            User obj = u.get(i);
            List<Song> s = obj.getSongList();
            for(int j = 0; j < s.size(); j++){
                temp = s.get(j);
                if(temp != null){
                    if (temp.getArtist().equals(artist))
                        songs.add(s.get(j));
                }
            }
        }
        return songs;
    }
    public void displayRoyalties(){
        System.out.println(String.format("Amount %-8s", "Artist"));
        System.out.println("----------------");
        ArrayList<Song> uList = new ArrayList(uniqueDownloads());

        royalties.forEach((key, value) -> System.out.println(String.format("$%.2f  %-15s",value, key)));
    }

    public TreeSet<Song> uniqueDownloads(){
        TreeSet<Song> sList;
        sList =  new TreeSet<Song>();
        for(int i = 0; i < downloadedSongs.size(); i++){
            if(!sList.contains(downloadedSongs.get(i))) {
                sList.add(downloadedSongs.get(i));
            }
        }
        return sList;
    }

    public ArrayList<Pair<Integer, Song>> songsByPopularity(){
        ArrayList<Pair<Integer, Song>> popular;
        popular = new ArrayList<Pair<Integer, Song>>();
        Song[] temp = uniqueDownloads().toArray(new Song[uniqueDownloads().size()]);
        Pair pair;
        int count = 0;
        for(int i = 0; i < uniqueDownloads().size(); i++){
            for(int j = 0; j < downloadedSongs.size(); j++){
                if(downloadedSongs.get(j).getTitle().equals(temp[i].getTitle())){
                    count++;
                }
            }
            pair = new Pair<Integer, Song>(count,temp[i]);
            popular.add(pair);
            count = 0;
        }
        Collections.sort(popular, new Comparator<Pair<Integer, Song>>() {
            public int compare(Pair<Integer, Song> p1, Pair<Integer, Song> p2) {
                return p2.getKey().compareTo(p1.getKey());
            }
        });
        return popular;
    }


    public List<Song> getDownloadedSongs() {
        return downloadedSongs;
    }
}
