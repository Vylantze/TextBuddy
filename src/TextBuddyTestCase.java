import static org.junit.Assert.*;
import org.junit.Test;

public class TextBuddyTestCase {
	String[] inputs = {
			"W15 = Rewrite",
			"",
			"clear",
			"Hello World",
			"turkey save me",
			"display 5",
			"display5",
			"delete5",
			"add turkey bacon",
			"add unlimited blade works",
			"add pie",
			"addshit",
			"add khairul",
			"add killed a cat",
			"add",
			"add you monster",
			"add cat kills you",
			"add added",
			"display",
			"delete 10",
			"delete -1",
			"delete 4",
			"display",
			"delete 1",
			"delete 5",
			"display",
			"eat cake",
			"exit"
	};
	String[] expected = {
			"\"W15 = Rewrite\" is not a valid command dood!",
			"\"\" is not a valid command dood!",
			"All content has been deleted from , dood",
			"\"Hello World\" is not a valid command dood!",
			"\"turkey save me\" is not a valid command dood!",
			" is most unfortunately empty",
			"\"display5\" is not a valid command dood!",
			"\"delete5\" is not a valid command dood!",
			"Added \"turkey bacon\" to ",
			"Added \"unlimited blade works\" to ",
			"Added \"pie\" to ",
			"\"addshit\" is not a valid command dood!",
			"Added \"khairul\" to ",
			"Added \"killed a cat\" to ",
			"Failed to add \"\" to ",
			"Added \"you monster\" to ",
			"Added \"cat kills you\" to ",
			"Added \"added\" to ",
			"1. turkey bacon\n2. unlimited blade works\n3. pie\n4. khairul\n5. killed a cat\n6. you monster\n7. cat kills you\n8. added",
			"10 is an invalid number dood!",
			"-1 is an invalid number dood!",
			"\"khairul\" has been deleted from ",
			"1. turkey bacon\n2. unlimited blade works\n3. pie\n4. killed a cat\n5. you monster\n6. cat kills you\n7. added",
			"\"turkey bacon\" has been deleted from ",
			"\"cat kills you\" has been deleted from ",
			"1. unlimited blade works\n2. pie\n3. killed a cat\n4. you monster\n5. added",
			"\"eat cake\" is not a valid command dood!",
			"Kthxbye"
	};
	
	@Test
	public void testCaseCE1() {
		/*
		int num = 0;
		assertEquals(expected[i], TextBuddy.executeCommand(inputs[i]));//*/
		for (int i=0;i<27;i++) {
			assertEquals(expected[i], TextBuddy.executeCommand(inputs[i]));
		}
	}
	//*/
	@Test
	public void testCaseCE2_1() {
		TextBuddy.executeCommand("sort");
		String output = 
				"1. added\n" +
				"2. killed a cat\n" +
				"3. pie\n" +
				"4. unlimited blade works\n" + 
				"5. you monster";
		assertEquals(output, TextBuddy.executeCommand("display"));
	}
	
	@Test
	public void testCaseCE2_2() {
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add pancake");
		TextBuddy.executeCommand("add pie");
		TextBuddy.executeCommand("add 10");
		TextBuddy.executeCommand("add 5");
		TextBuddy.executeCommand("add 1");
		TextBuddy.executeCommand("add ultimate");
		
		TextBuddy.executeCommand("sort");
		String output =
				"1. 1\n" +
				"2. 10\n" + 
				"3. 5\n" + 
				"4. pancake\n" +
				"5. pie\n" +
				"6. ultimate";
		
		assertEquals(output, TextBuddy.executeCommand("display"));
	}
	
	@Test
	public void testCaseCE2_3() {
		TextBuddy.executeCommand("add turkey");
		TextBuddy.executeCommand("add EXCALIBURRRR");
		TextBuddy.executeCommand("add SEIIIBAAAAAA");
		
		String output = 
				"1. pancake\n" +
				"2. pie\n" +
				"3. ultimate\n" +
				"4. turkey";
		
		assertEquals(output, TextBuddy.executeCommand("search e"));
	}
	
	@Test
	public void testCaseCE2_4() {
		String output = 
				"1. pancake\n" +
				"2. turkey";
		
		assertEquals(output, TextBuddy.executeCommand("search ke"));
	}
	
	@Test
	public void testCaseCE2_5() {		
		String output = 
				"1. EXCALIBURRRR\n" +
				"2. SEIIIBAAAAAA";
		
		assertEquals(output, TextBuddy.executeCommand("search E"));
	}
	
	@Test
	public void testCaseCE2_6() {
		TextBuddy.executeCommand("add one thousand");
		
		String output = "Search term \"search\" could not be found";
		
		assertEquals(output, TextBuddy.executeCommand("search search"));
	}
}