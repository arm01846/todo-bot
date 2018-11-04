package bot.todo;

import org.bson.Document;

public class MongoQuery {
    public static final Document CUSTOM_PROJECTION = Document.parse(
            "{\n" +
            "   $project: {\n" +
            "        user: 1,\n" +
            "        finished: {\n" +
            "            $cond: {\n" +
            "                if: {\n" +
            "                    $eq: [\"$isFinished\", true]\n" +
            "                },\n" +
            "                then: \"$count\",\n" +
            "                else: \"$SKIP\"\n" +
            "            }\n" +
            "        },\n" +
            "        unfinished: {\n" +
            "            $cond: {\n" +
            "                if: {\n" +
            "                    $eq: [\"$isFinished\", false]\n" +
            "                },\n" +
            "                then: \"$count\",\n" +
            "                else: \"$SKIP\"\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}"
    );

    public static final Document CUSTOM_GROUP = Document.parse("{\n" +
            "    $group: {\n" +
            "        _id: \"$user\",\n" +
            "        user: {\n" +
            "            \"$first\": \"$user\"\n" +
            "        },\n" +
            "        finished: {\n" +
            "            \"$max\": \"$finished\"\n" +
            "        },\n" +
            "        unfinished: {\n" +
            "            \"$max\": \"$unfinished\"\n" +
            "        }\n" +
            "    }\n" +
            "}"
    );
}
