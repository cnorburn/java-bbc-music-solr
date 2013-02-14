package bbc.forge.music.ion;

import java.util.Properties;

import bbc.forge.config.PropertiesMuncher;
import bbc.forge.music.ion.JSONPage;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConstructUrl {

	protected final Log log = LogFactory.getLog(getClass());

	private static Integer pageCount;	
	private Integer page;
	private static Integer itemCount;
	
	//private Integer perPage=100;
	//private String ion="http://www.bbc.co.uk/iplayer/ion/listview/block_type/episode/category/9100006/clips/exclusive/expose_tags/1/media_set/pc/page/1/perpage/1/sort/dateavailable/sortdir/desc/format/json";
	//private String ion_1="http://www.bbc.co.uk/iplayer/ion/listview/block_type/episode/category/9100006/clips/exclusive/expose_tags/1/media_set/pc/page/";
	//private String ion_2="/perpage/" + perPage.toString() + "/sort/dateavailable/sortdir/desc/format/json";
	
	public static Properties props  = PropertiesMuncher.munch();

	public String getURL(String order) throws Exception{
		
		if (this.page==null || this.page==0){
			try {
				getItemCount(order);
			}catch (Exception e) {
				throw new Exception("Ion connection error - " + e.getMessage());
			}	
			this.setPage(1);
		}
		else
			this.setPage(page + 1);
		
		if (page>pageCount)
			return null;
		else{
			if (order=="desc")
				return props.getProperty("ion.url.2") + page.toString() + "/perpage/" + props.getProperty("ion.perpage");
			else
				return props.getProperty("ion.url.4") + page.toString() + "/perpage/" + props.getProperty("ion.perpage");
		}
			
	}
	
	
	private void getItemCount(String order) throws Exception{
		JSONObject json = null;
		JSONPage page=new JSONPage();
		
		try{
			if (order=="desc")
				json=JSONObject.fromObject(page.getJSONPage(props.getProperty("ion.url.1")));
			else if(order=="asc")
				json=JSONObject.fromObject(page.getJSONPage(props.getProperty("ion.url.3")));
		}catch (Exception e) {
			throw new Exception("Ion connection error - " + e.getMessage());
		}	
		
		if (json.containsKey("count")){	
			this.itemCount=(Integer.parseInt((String) json.get("count")));	
			this.pageCount = ((itemCount - 1) / Integer.parseInt(props.getProperty("ion.perpage")) + 1);
		}
	}
	
		
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageCount(String order)  throws Exception{
			try {
				getItemCount(order);
			}catch (Exception e) {
				throw new Exception("Ion connection error - " + e.getMessage());
			}	
		
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	
	
}
