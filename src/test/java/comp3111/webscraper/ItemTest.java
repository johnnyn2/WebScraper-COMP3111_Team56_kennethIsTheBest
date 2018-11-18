package comp3111.webscraper;


import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {

	@Test
	public void testSetTitle() {
		Item i = new Item();
		i.setTitle("ABCDE");
		assertEquals(i.getTitle(), "ABCDE");
	}
	
	@Test
	public void testSetPrice() {
		Item i = new Item();
		i.setPrice(5000.3);
		/*
		 *  Test the relative error in terms of ULPs since the price is stored in double (which allows floating point values).  
		 *  Select a tolerance somewhere between 1 and 10 ULPs. 
		 *  For example, here I specify that the actual result needs to be within 5 ULPs of the true value:
		 */
		assertEquals(i.getPrice(), 5000.3, 5*Math.ulp(i.getPrice()));
	}
	
	@Test
	public void testSetUrl() {
		Item i = new Item();
		i.setUrl("https://newyork.craigslist.org/");
		assertEquals(i.getUrl(), "https://newyork.craigslist.org/");
	}
	
	@Test
	public void testSetPostdate() {
		Item i = new Item();
		i.setPostdate("2018-11-18 00:24");
		assertEquals(i.getPostdate(), "2018-11-18 00:24");
	}
	
	@Test
	public void testCompareTo() {
		Item i = new Item();
		Item j = new Item();
		i.setPrice(2000.0);
		j.setPrice(3000.0);
		assertEquals(-1, i.compareTo(j));
	}
}
