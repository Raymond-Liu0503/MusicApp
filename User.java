import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.setOwner;
import static java.nio.file.Files.size;

public class User {
  private String     userName;
  private boolean    online;
  private ArrayList<Song> songList;
  
  public User()  { this(""); }
  
  public User(String u)  {
    userName = u;
    online = false;
    songList = new ArrayList();
  }
  
  public String getUserName() { return userName; }
  public boolean isOnline() { return online; }
  public List<Song> getSongList(){return songList;}
  public void addSong(Song s){
    s.setOwner(this);
    songList.add(s);
  }
  public int totalSongTime(){
    int total = 0;
    for(int i = 0; i < songList.size(); i++){
      Song obj = songList.get(i);
      total = total + obj.getSeconds();
    }
    return total;
  }
  public void register(MusicExchangeCenter m){
    m.registerUser(this);
  }
  public void logon(){
    online = true;
  }
  public void logoff(){
    online = false;
  }

  public List requestCompleteSonglist(MusicExchangeCenter m){
    List<Song> songL;
    ArrayList<String> result;
    songL = new ArrayList();
    result = new ArrayList();
    songL = m.allAvailableSongs();
    int title_len;
    int art_len;
    int own_len;
    String temp;

    temp = String.format("%-36s%-20s TIME  OWNER", "TITLE"," ARTIST");
    result.add(temp);
    temp = "";
    result.add(temp);

    for(int i = 0; i < songL.size();i++){
      String t;
      title_len = 35;
      art_len = 20;
      own_len = 15;

      if(songL.get(i).getSeconds() < 10){
        t = songL.get(i).getMinutes() + ":0" + songL.get(i).getSeconds();
      }else{
        t = songL.get(i).getMinutes() + ":" + songL.get(i).getSeconds();
      }

      if(i<9){
        temp = String.format("%-1s."+"%-" + title_len + "s" + "%-" + art_len + "s" + "%-6s" + "%-" + own_len + "s",i+1, songL.get(i).getTitle(), songL.get(i).getArtist(), t, songL.get(i).getOwner().getUserName());
      }else{
        temp = String.format("%-2s."+"%-" + (title_len-1) + "s" + "%-" + art_len + "s" + "%-6s" + "%-" + own_len + "s",i+1, songL.get(i).getTitle(), songL.get(i).getArtist(), t, songL.get(i).getOwner().getUserName());
      }
      result.add(temp);
    }

    return result;
  }

  public List requestSonglistByArtist(MusicExchangeCenter m, String artist){
    List<Song> songL;
    ArrayList<String> result;
    songL = new ArrayList();
    result = new ArrayList();
    songL = m.availableSongsByArtist(artist);
    int title_len;
    int art_len;
    int own_len;
    String temp;

    temp = String.format("%-36s%-20s TIME  OWNER", "TITLE"," ARTIST");
    result.add(temp);
    temp = "";
    result.add(temp);

    for(int i = 0; i < songL.size();i++){
      String t;
      title_len = 35;
      art_len = 20;
      own_len = 15;

      if(songL.get(i).getSeconds() < 10){
        t = songL.get(i).getMinutes() + ":0" + songL.get(i).getSeconds();
      }else{
        t = songL.get(i).getMinutes() + ":" + songL.get(i).getSeconds();
      }

      if(i<9){
        temp = String.format("%-1s."+"%-" + title_len + "s" + "%-" + art_len + "s" + "%-6s" + "%-" + own_len + "s",i+1, songL.get(i).getTitle(), songL.get(i).getArtist(), t, songL.get(i).getOwner().getUserName());
      }else{
        temp = String.format("%-2s."+"%-" + (title_len-1) + "s" + "%-" + art_len + "s" + "%-6s" + "%-" + own_len + "s",i+1, songL.get(i).getTitle(), songL.get(i).getArtist(), t, songL.get(i).getOwner().getUserName());
      }
      result.add(temp);
    }

    return result;
  }

  public void downloadSong(MusicExchangeCenter m, String title, String ownerName){
    Song temp = m.getSong(title, ownerName);
    if(temp != null){
      songList.add(temp);
    }
  }

  public String toString()  {
    String s = "" + userName + ": "+ songList.size() + "(";
    if (!online) s += "not ";
    return s + "online)";
  }
}
