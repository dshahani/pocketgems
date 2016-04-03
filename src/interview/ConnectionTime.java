package interview;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConnectionTime
{
	public static void main1(String[] args) throws FileNotFoundException, IOException
	{
		String filename = "input_1.txt";
		if (args.length > 0)
		{
			filename = args[0];
		}

		String answer = parseFile(filename);
		System.out.println(answer);
	}

	static String parseFile(String filename) throws FileNotFoundException, IOException
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

		return parseLines(allLines.toArray(new String[allLines.size()]));
	}

	public static void main(String[] args)
	{
		String[] lines = { "(11/01/2015-04:00:00) :: START", "(11/01/2015-04:00:00) :: CONNECTED",
				"(11/01/2015-04:30:00) :: DISCONNECTED", "(11/01/2015-04:45:00) :: CONNECTED",
				"(11/01/2015-05:00:00) :: SHUTDOWN" };
		System.out.println(parseLines(lines));
	}

	static String parseLines(String[] lines)
	{
		String DATE_FORMAT_NOW = "MM/dd/yyyy-HH:mm:ss";
		DateFormat format = new SimpleDateFormat(DATE_FORMAT_NOW);
		Map<String, LinkedList<String>> statusMap = new HashMap<String, LinkedList<String>>();
		String[] line;
		String status;
		String dateTime;
		LinkedList<String> timeLst;
		int connectionTime = 0, index = 0, totalTime = 0;
		Date startDate, endDate;
		String startTime, endTime;

		for (int i = 0; i < lines.length; i++)
		{
			line = lines[i].split("::");
			status = line[1];
			dateTime = line[0].substring(1, line[0].length() - 2);
			if (statusMap.containsKey(status.substring(1)))
			{
				timeLst = statusMap.get(status.substring(1));
				timeLst.addLast(dateTime);
				statusMap.put(status.substring(1), timeLst);
			} else
			{
				timeLst = new LinkedList<String>();
				timeLst.addLast(dateTime);
				statusMap.put(status.substring(1), timeLst);
			}
		}
		try
		{
			startTime = statusMap.get("START").get(0);
			endTime = statusMap.get("SHUTDOWN").get(0);
			startDate = format.parse(startTime);
			endDate = format.parse(endTime);
			totalTime = (int) ((endDate.getTime() / 60000) - (startDate.getTime() / 60000));

			LinkedList<String> connected = statusMap.get("CONNECTED");
			LinkedList<String> disconnected = statusMap.get("DISCONNECTED");

			for (String time : connected)
			{
				startTime = time;
				if (disconnected.size() > index)
				{
					endTime = disconnected.get(index);
				} else
				{
					endTime = statusMap.get("SHUTDOWN").get(0);
				}
				startDate = format.parse(startTime);
				endDate = format.parse(endTime);
				connectionTime += (int) ((endDate.getTime() / 60000) - (startDate.getTime() / 60000));
				index++;
			}

		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		int res = (int) ((connectionTime / (double) totalTime) * 100);
		return res + "%";
	}
}