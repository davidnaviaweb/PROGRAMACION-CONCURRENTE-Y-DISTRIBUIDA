package hadoop;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCounterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private final static Pattern LOG_PATTERN =  Pattern.compile("\\b(INFO|WARN|SEVERE)\\b");

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        Matcher matcher = LOG_PATTERN.matcher(line);

        while (matcher.find()) {
            Text currentWord = new Text(matcher.group());
            context.write(currentWord, one);
        }
    }
}