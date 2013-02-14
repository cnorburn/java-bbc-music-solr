package bbc.forge.music.pojo;

import java.util.ArrayList;

public class Record {
	
	public String mbid;
	public String pid;
	public String clipTitle;
	public String artist;
	public ArrayList<String> artists=new ArrayList<String>();
	public String priority="0";
	public String mediaType;
	
	public String getMbid() {
		return mbid;
	}
	public void setMbid(String mbid) {
		if(mbid.contains("/")){
			String[] split=mbid.split("/");
			this.mbid =split[split.length-1];
		}else
			this.mbid=mbid;		
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getClipTitle() {
		return clipTitle;
	}
	public void setClipTitle(String clipTitle) {
		this.clipTitle = clipTitle;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public ArrayList<String> getArtists() {
		return artists;
	}
	public void setArtists(String artist) {
		this.artists.add(artist);
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
}
