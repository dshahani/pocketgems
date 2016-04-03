package interview;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PathsBetweenNodes
{
	public static void main2(String[] args) throws FileNotFoundException, IOException
	{
		String filename = "input_1.txt";
		if (args.length > 0)
		{
			filename = args[0];
		}

		List<String> answer = parseFile(filename);
		System.out.println(answer);
	}

	static List<String> parseFile(String filename) throws FileNotFoundException, IOException
	{
		/*
		 * Don't modify this function
		 */
		BufferedReader input = new BufferedReader(new FileReader(filename));
		List<String> allLines = new ArrayList<String>();
		String line;
		while ((line = input.readLine()) != null)
		{
			allLines.add(line);
		}
		input.close();

		return parseLines(allLines);
	}

	public static void main(String[] args)
	{
		List<String> lines = new ArrayList<String>();
		lines.add("A C");
		/*
		 * lines.add("A : B D"); lines.add("B : C D"); lines.add("D : C");
		 */

		lines.add("A : E");
		lines.add("B : C");
		lines.add("C : E");
		lines.add("D : B");

		List<String> paths = new ArrayList<String>();
		paths = parseLines(lines);

		for (String str : paths)
		{
			System.out.println(str);
		}

	}

	static List<String> parseLines(List<String> lines)
	{
		if (lines == null)
		{
			return null;
		} else if (lines.isEmpty())
		{
			return new ArrayList<String>();
		}
		String srcDes = lines.get(0);
		char src = srcDes.split(" ")[0].charAt(0);
		char des = srcDes.split(" ")[1].charAt(0);
		Map<Character, LinkedList<Character>> adjMap = new HashMap<Character, LinkedList<Character>>();
		String nodes;
		String[] node;
		LinkedList<Character> nodeLst;
		for (int i = 1; i < lines.size(); i++)
		{
			nodeLst = new LinkedList<Character>();
			nodes = lines.get(i);
			node = nodes.split(":");
			char key = node[0].charAt(0);
			node = node[1].split(" ");
			for (int j = 1; j < node.length; j++)
			{
				nodeLst.addLast(node[j].charAt(0));
			}
			adjMap.put(key, nodeLst);
		}

		if (!adjMap.containsKey(src))
		{
			return new ArrayList<String>();
		}

		LinkedList<Character> visited = new LinkedList<Character>();
		visited.add(src);
		List<String> paths = new ArrayList<String>();
		depthFirst(adjMap, visited, des, paths);
		return paths;
	}

	static void depthFirst(Map<Character, LinkedList<Character>> graph, LinkedList<Character> visited, Character end,
			List<String> paths)
	{

		if (!graph.containsKey(visited.getLast()))
		{
			return;
		}

		LinkedList<Character> nodes = graph.get(visited.getLast());
		for (Character node : nodes)
		{
			if (visited.contains(node))
			{
				continue;
			}
			if (node.equals(end))
			{
				visited.add(node);
				printPath(visited, paths);
				visited.removeLast();
				break;
			}
		}
		for (Character node : nodes)
		{
			if (visited.contains(node) || node.equals(end))
			{
				continue;
			}
			visited.addLast(node);
			depthFirst(graph, visited, end, paths);
			visited.removeLast();
		}
	}

	private static void printPath(LinkedList<Character> visited, List<String> paths)
	{
		StringBuffer output = new StringBuffer("");
		for (Character node : visited)
		{
			output.append(node + " ");
		}
		paths.add(output.toString());
	}

}