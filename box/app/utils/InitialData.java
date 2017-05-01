package utils;

import java.util.Arrays;
import java.util.List;

import models.DestinationBean;
import models.ListBean;
import models.SkiResortBean;
import models.UserBean;
import models.DestinationBean.DestinationType;
import models.ListBean.ListType;
import play.Logger;


/**
 * @author Ivana Frankic
 *
 */
public class InitialData {
	// Ski resorts
	private static DestinationBean lesarc = new SkiResortBean(
			"Les Arcs",
			"France",
			"The five areas—Bourg-Saint-Maurice, Arc 1600, Arc 1800, Arc 1950, and Arc 2000—are situated at an altitude spanning from 810 to 3226 metres, although skiing is mostly possible above 1200 metres. The ski area consists of 106 runs, 54 lifts, and 200 kilometres of descent. The highest peak in the resort is the Aiguille Rouge (Red Needle) from where is a 7 km long piste with 2026 metres in vertical drop down to the Village Villaroger. Since the opening of the Vanoise Express cable car in December 2003, Les Arcs has become part of the Paradiski group of ski-connected resorts, which also includes the La Plagne area. Paradiski in total has 425 km of pistes.",
			"http://en.lesarcs.com/", "lesarcs.png", "paradiski2.png", 2466, 425, 17, 64, 44, 1);

	private static DestinationBean laplagne = new SkiResortBean(
			"La Plagne",
			"France",
			"Paradiski ski area : family ski resort in France. Treat yourself with a Alps ski holidays at La Plagne. This ski resort in France proposes 425 km of ski runs for skiing with family or between friends. Parents like children, enjoy to learn the pleasures of skiing in la Plagne. Thrill seekers, practice the freestyle skiing in the special area developed for this activity. Test the bobsleigh, ski on a glacier ... Enjoy your Family ski holidays in a french family ski resort.",
			"http://winter.la-plagne.com/", "laplagne.png", "paradiski1.png", 3250, 174, 19, 34, 70, 9);

	private static DestinationBean les2alpes = new SkiResortBean(
			"Les 2 Alpes",
			"France",
			"This vibrant, sporty and fashionable international ski resort is among the biggest and most famous in the world. Les 2 Alpes was built in 1946 on the former high pastures of Venosc and Mont de Lans, two parish villages in the Isère where the northern and southern Alps meet.",
			"http://www.les2alpes.com/en", "les2alpes.png", "les2alpes.png", 3600, 141, 12, 22, 45, 18);

	private static DestinationBean valdisere = new SkiResortBean(
			"Val d'Isère",
			"France",
			"Val d’Isère is one of the most famous ski destinations in the world. This Alpine playground in the heart of the Savoie Mont Blanc region of France offers 300km of groomed pistes and – most important in these uncertain winters – guaranteed snow cover from the end of November until the beginning of May.",
			"http://www.valdisere.com/", "valdisere.png", "valdisere.png", 3428, 300, 52, 78, 170, 21);

	private static DestinationBean tignes = new SkiResortBean(
			"Tignes",
			"France",
			"Just as Paris has the Eiffel Tower so London has “Big Ben” and New York the Statue of Liberty; when it comes to Tignes, it’s the lake, the glacier and its hydroelectric dam that stand out. From natural masterpieces to a gigantic man-made monument, take a look at three of the best features in Tignes…",
			"http://en.tignes.net/", "tignes.png", "tignes.png", 3456, 89, 16, 22, 36, 21);

	private static DestinationBean kopaonik = new SkiResortBean(
			"Kopaonik",
			"Serbia",
			"Kopaonik is the largest ski resort in Serbia with excellently prepared runs, which offers unlimited joy to all categories of skiers, from beginners to highly skilled ones. Kopaonik ski resort comprises of about 55km of runs for alpine skiing and 12 km of runs for cross country (nordic) skiing. For those who enjoy skiing at night, we have prepared a lighted run 'Malo Jezero' in the very centre of the resort. Apart from that, for the youngest skiers and all those who are taking their first ski steps, we have prepared a secured 'ski kindergarten' area with a conveyor belt and a carousel which makes learning basic skiing skills easier.",
			"http://www.eng.infokop.net/", "kopaonik.png", "kopaonik.png", 2017, 55, 11, 22, 20, 21);

	private static DestinationBean jahorina = new SkiResortBean(
			"Jahorina",
			"Bosnia and Herzegovina",
			"Jahorina Olympic Ski Resort is the biggest and most popular ski resort in Bosnia and Herzegovina and offers a variety of outdoor sports and activities. It is primarily a destination for alpine skiing, snowboarding, hiking, and sledding, with over 40 km (25 mi) of ski slopes and modern facilities. ",
			"http://oc-jahorina.com/en/", "jahorina.png", "jahorina.png", 1916, 25, 1, 15, 6, 11);

	private static DestinationBean bansko = new SkiResortBean(
			"Bansko",
			"Bulgaria",
			"Bansko ski resort is the best winter resort in Bulgaria with the longest ski runs and the richest cultural history. The town of Bansko is situated at the foot of the Pirin mountain in the south-western Bulgaria, 160 km from Sofia. The resort offers excellent ski and snowboard conditions and infrastructure. A lot of hotels of various standards and prices are available for booking on-line. Regarding Apres ski - the resort offers hundreds of bars and traditional restaurants called 'mehana'. Bansko mountain resort is an excellent choice for your ski and snowboard holiday in Bulgaria.",
			"http://www.banskoski.com/en", "bansko.png", "bansko.png", 2560, 65, 2, 11, 8, 2);

	private static DestinationBean nassfeld = new SkiResortBean(
			"Nassfeld",
			"Austria",
			"Amazingly generous, vast, snow-reliable and almost unbeatable when it comes to the mountain panorama; anyone who comes to Nassfeld, one of Austria’s TOP 10 ski resorts, can expect quite something. But the ski resort can even top that and provides its guests with moments of surprise that bring a smile to their faces. Special aha moments for sports and leisure skiers are waiting at every “corner“ of the well-developed ski resorts with its 30 modern mountain railways and lifts and 110 kilometres of pistes. Whether snow park, fun slope, freeride areas or Ski Movie race track – simply look out for the “Nice Surprise“ information boards and discover a completely new side to winter sport!",
			"http://www.nassfeld.at/", "nassfeld.png", "nassfeld.png", 2200, 110, 11, 69, 30, 0);
	
	private static DestinationBean mayrhofen = new SkiResortBean(
			"Mayrhofen",
			"Austria",
			"An unforgettable mountain experience in one of the most active regions of the Alps – not just a motto but Mayrhofen's promise to its guests. The popular skiing area offers 136 kilometres of piste, 57 lifts and countless other winter sports activities for action enthusiasts and families as well as those who prefer a more leisurely style of vacation.",
			"http://www.mayrhofen.at/", "mayrhofen.png", "mayrhofen.jpg", 2500, 136, 8, 23, 16, 0);

	// Cities
	private static DestinationBean lisbon = new DestinationBean(
			DestinationType.CITY, "Lisbon",
			"Portugal",
			"Lisbon is one of the oldest cities in the world, and the oldest in Western Europe, predating other modern European capitals such as London, Paris and Rome by centuries. Julius Caesar made it a municipium called Felicitas Julia, adding to the name Olissipo. Ruled by a series of Germanic tribes from the 5th century, it was captured by the Moors in the 8th century. In 1147, the Crusaders under Afonso Henriques reconquered the city and since then it has been a major political, economic and cultural centre of Portugal. Unlike most capital cities, Lisbon's status as the capital of Portugal has never been granted or confirmed officially – by statute or in written form. Its position as the capital has formed through constitutional convention, making its position as de facto capital a part of the Constitution of Portugal.",
			"lisbon-portugal-guide.com", "LSB.png", "LSB.png");

	private static DestinationBean london = new DestinationBean(
			DestinationType.CITY, "London",
			"England",
			"London is a leading global city in the arts, commerce, education, entertainment, fashion, finance, healthcare, media, professional services, research and development, tourism, and transportation. It is crowned as the world's largest financial centre and has the fifth- or sixth-largest metropolitan area GDP in the world. London is a world cultural capital. It is the world's most-visited city as measured by international arrivals and has the world's largest city airport system measured by passenger traffic. London is the world's leading investment destination, hosting more international retailers and ultra high-net-worth individuals than any other city. London's universities form the largest concentration of higher education institutes in Europe. In 2012, London became the first city to have hosted the modern Summer Olympic Games three times.",
			"http://www.visitlondon.com/", "london.png", "londonbgr.png");

	private static DestinationBean stockholm = new DestinationBean(
			DestinationType.CITY, "Stockholm",
			"Sweden",
			"Stockholm is the cultural, media, political, and economic centre of Sweden. The Stockholm region alone accounts for over a third of the country's GDP, and is among the top 10 regions in Europe by GDP per capita. It is an important global city, and the main centre for corporate headquarters in the Nordic region. The city is home to some of Europe's top ranking universities, such as the Stockholm School of Economics, Karolinska Institute and Royal Institute of Technology (KTH). It hosts the annual Nobel Prize ceremonies and banquet at the Stockholm Concert Hall and Stockholm City Hall. One of the city's most prized museums, the Vasa Museum, is the most visited non-art museum in Scandinavia. The Stockholm metro, opened in 1950, is well known for its decoration of the stations; it has been called the longest art gallery in the world. Sweden's national football arena is located north of the city centre, in Solna. Ericsson Globe, the national indoor arena, is in the southern part of the city. The city was the host of the 1912 Summer Olympics, and hosted the equestrian portion of the 1956 Summer Olympics otherwise held in Melbourne, Victoria, Australia.",
			"https://www.visitstockholm.com/", "stockholm.svg", "stockholm.png");

	private static DestinationBean tokyo = new DestinationBean(
			DestinationType.CITY, "Tokyo",
			"Japan",
			"Prior to 1868, Tokyo was known as Edo. A small castle town in the 16th century, Edo became Japan's political center in 1603 when Tokugawa Ieyasu established his feudal government there. A few decades later, Edo had grown into one of the world's most populous cities. With the Meiji Restoration of 1868, the emperor and capital moved from Kyoto to Edo, which was renamed Tokyo (\"Eastern Capital\"). Large parts of Tokyo were destroyed in the Great Kanto Earthquake of 1923 and in the air raids of 1945. Today, Tokyo offers a seemingly unlimited choice of shopping, entertainment, culture and dining to its visitors. The city's history can be appreciated in districts such as Asakusa, and in many excellent museums, historic temples and gardens. Contrary to common perception, Tokyo also offers a number of attractive green spaces in the city center and within relatively short train rides at its outskirts.",
			"http://www.japan-guide.com/e/e2164.html", "tokyo.png", "tokyo.png");

	private static DestinationBean reykjavik = new DestinationBean(
			DestinationType.CITY, "Reykjavik",
			"Iceland",
			"Reykjavík is believed to be the location of the first permanent settlement in Iceland, which, according to Ingólfur Arnarson, was established in AD 874. Until the 19th century, there was no urban development in the city location. The city was founded in 1786 as an official trading town and grew steadily over the next decades, as it transformed into a regional and later national centre of commerce, population, and governmental activities. It is among the cleanest, greenest, and safest cities in the world.",
			"http://www.visitreykjavik.is/", "reykjavik.png", "reykjavik.jpg");

	private static DestinationBean belgrade = new DestinationBean(
			DestinationType.CITY, "Belgrade",
			"Serbia",
			"Outspoken, adventurous, proud and audacious: Belgrade (Београд) is by no means a 'pretty' capital, but its gritty exuberance makes it one of the most happening cities in Europe. While it hurtles towards a brighter future, its chaotic past unfolds before your eyes: socialist blocks are squeezed between art nouveau masterpieces, and remnants of the Habsburg legacy contrast with Ottoman relics.\n\nIt's here where the Sava River meets the Danube, contemplative parkland nudges hectic urban sprawl, and old-world culture gives way to new-world nightlife.\n\nGrandiose coffee houses and smoky dives all find rightful place along Knez Mihailova, a lively pedestrian boulevard flanked by historical buildings all the way to the ancient Kalemegdan Citadel, crown of the city. The old riverside Savamala quarter has gone from ruin to resurrection, and is the city's creative headquarters. Deeper in Belgrade's bowels are museums guarding the cultural, religious and military heritage of the country.",
			"http://www.tob.rs/en/", "belgrade.png", "belgrade.jpg");

	private static DestinationBean berlin = new DestinationBean(
			DestinationType.CITY, "Berlin",
			"Germany",
			"Berlin's combo of glamour and grit is bound to mesmerise anyone keen to explore its vibrant culture, cutting-edge architecture, fabulous food, intense parties and tangible history.\n\nBerlin is a big multicultural metropolis but deep down it maintains the unpretentious charm of an international village. Locals follow the credo 'live and let live' and put greater emphasis on personal freedom and a creative lifestyle than on material wealth and status symbols. Cafes are jammed at all hours, drinking is a religious rite and clubs keep going until the wee hours or beyond. Size-wise, Berlin is pretty big but its key areas are wonderfully compact and easily navigated on foot, by bike or by using public transport.",
			"http://www.visitberlin.de/en", "berlin.png", "berlin.jpg");

	private static DestinationBean sanfrancisco = new DestinationBean(
			DestinationType.CITY, "San Francisco",
			"California",
			"Grab your coat and a handful of glitter, and enter the land of fog and fabulousness. So long, inhibitions; hello, San Francisco. \n\nConsider permission permanently granted to step up, strip down and go too far: other towns may surprise you, but in San Francisco you will surprise yourself. Good times and social revolutions tend to start here, from manic Gold Rushes to blissful hippie Be-Ins. If there’s a skateboard move yet to be busted, a technology still unimagined, a poem left unspoken or a green scheme untested, chances are it’s about to happen here. Yes, right now: this town has lost almost everything in earthquakes and dot-com gambles, but never its nerve.",
			"http://www.sftravel.com/", "sf.png", "sf.jpg");

	private static DestinationBean cusco = new DestinationBean(
			DestinationType.CITY, "Cuzco & the Sacred Valley",
			"Peru",
			"Incas deemed this spot the belly button of the world. A visit to Cuzco tumbles you back into the cosmic realm of ancient Andean culture – knocked down and fused with the colonial splendors of Spanish conquest, only to be repackaged as a thriving tourist mecca. Yet Cuzco is only the gateway. Beyond lies the Sacred Valley, Andean countryside dotted with villages, high altitude hamlets and ruins linked by trail and railway tracks to the continent's biggest draw – Machu Picchu.\n\nOld ways are not forgotten here. Colorful textiles keep vivid the past, as do the wild fiestas and carnivals where pagan tradition meets solemn Catholic ritual. A stunning landscape careens from Andean peaks to orchid-rich cloud forests and Amazon lowlands. Explore it on foot or by fat tire, rafting wild rivers or simply braving the local buses to the remote and dust-worn corners of this far-reaching, culturally rich department.",
			"http://www.andeantravelweb.com/peru/destinations/cusco/", "cusco.png", "cusco.jpg");

	private static DestinationBean marrakesh = new DestinationBean(
			DestinationType.CITY, "Marrakesh",
			"Morocco",
			"Got your map ready? Well, it's probably of little use to you here. Wrapped within the 19 kilometres of powder-pink pisé ramparts, the medina is Marrakesh's show-stopping sight of crowded souqs, where sheep carcasses swing from hooks next door to twinkling lamps, and narrow, doodling ochre-dusted lanes lead to nowhere. The main artery into this mazy muddle is the vast square of Djemaa el-Fna, where it's carnival night every night. Stroll between snail-vendors, soothsayers, acrobats and conjurers, musicians and slapstick acting troupes to discover the old city's frenetic pulse. The party doesn't end until the lights go out.",
			"https://www.visitstockholm.com/", "marrakesh.png", "marrakesh.jpg");

	// Beaches/Outdoor/Nature
	private static DestinationBean benagil = new DestinationBean(
			DestinationType.WILD, "Braia de Benagil",
			"PORTUGAL",
			"In a deep valley a few kilometres east of Carvoeiro is the little fishing village of Benagil. The beach here is possibly a clue as to what Carvoeiro was like many years ago. However, at Benagil there is still a busy little fishing fleet with boats that can be seen pulled up on the beach at the bottom of the slipway.",
			"https://www.travel-in-portugal.com/beaches/praia-de-benagil.htm", "surf.png", "benagil.jpg");
	
	private static DestinationBean rocha = new DestinationBean(
			DestinationType.WILD, "Praia da Rocha",
			"Portugal",
			"Five minutes' drive from Portimão, Praia da Rocha has one of the Algarve's best beaches, backed by ochre-red cliffs and the small 16th-century Fortaleza da Santa Catarina.Behind the beach looms the town; this has long known the hand of development, with high-rise condos and luxury hotels sprouting along the cliffside, and a row of restaurants, bars and clubs packed along the main thoroughfare. ",
			"https://www.lonelyplanet.com/portugal/praia-da-rocha", "rocha.png", "rocha.jpg");
	
	private static DestinationBean cabo = new DestinationBean(
			DestinationType.WILD, "Cabo de São Vicente",
			"Portugal",
			"Five kilometres from Sagres, Europe’s southwesternmost point is a barren headland, the last piece of home that Portuguese sailors once saw as they launched into the unknown. It's a spectacular spot: at sunset you can almost hear the hissing as the sun hits the sea. A red lighthouse houses the small but excellent Museu dos Faróis , showcasing Sagres' role in Portugal’s maritime history.",
			"http://www.algarve-tourist.com/Sagres-portugal/Cabo-Sao-Vincent-cape-Portugal.html", "palm.jpg", "cabo.jpg");
	
	private static DestinationBean covo = new DestinationBean(
			DestinationType.WILD, "Porto Côvo",
			"Portugal",
			"The appealingly ‘traditional cute’ Porto Côvo is worth a quick visit if you have the time. Perched on low cliffs with views over the sea, Porto Côvo is a former fishing village with a pretty square, cobbled streets lined with sun-bleached houses and a popular beach. ",
			"https://www.visitportugal.com/en/NR/exeres/FC7F817D-7858-4F2E-9B4F-E758A4EFD780", "flip.jpg", "covo.jpg");
	
	private static DestinationBean caminito = new DestinationBean(
			DestinationType.WILD, "Caminito del Rey",
			"Spain",
			"El Chorro gorge is particularly famous for the Caminito del Rey (King's Path) so named because Alfonso XIII walked along it when he opened the Guadalhorce hydroelectric dam in 1921. The path had fallen into severe disrepair by the late 1990s and became famously known as the most dangerous pathway in the world. After extensive restoration and a €5.5 million tab, it reopened in March 2015 and is now reputedly entirely safe and open to anyone with a reasonable head for heights.\n\nThe path hangs 100m above the river Guadalhorce and snakes around the cliffs, affording truly breathtaking views at every turn. The boardwalk is 2.9km long and constructed with a wooden slats and a 1.2m-high three-wire guard rail; in some sections the old crumbling path can be spied just below. There is a lot of information on the website, including reservation details. You should allow around four hours for the walk which includes returning to your access point. Note that the walk is linear, however there are regular buses that can take you back to your start point (€1.55, 20 minutes).\n\nThe most convenient public transport to the area is the train from Málaga which leaves eight times daily to Alora (40 minutes, €3.60). Check www.renfe.com for the timetable.",
			"http://www.caminitodelrey.info/en/", "caminito.jpg", "caminito.jpg");
	
	private static DestinationBean plitvice = new DestinationBean(
			DestinationType.WILD, "Plitvice Lakes",
			"Croatia",
			"Within the boundaries of this heavily forested national park, 16 crystalline lakes tumble into each other via a series of waterfalls and cascades. The mineral-rich waters carve through the rock, depositing tufa in continually changing formations. Clouds of butterflies drift above the 18km of wooden footbridges and pathways that snake around the edges and across the rumbling water.",
			"http://www.np-plitvicka-jezera.hr/en/", "plitvice.jpg", "plitvice.jpg");
	
	private static DestinationBean felsenmeer = new DestinationBean(
			DestinationType.WILD, "Felsenmeer \"Sea of Rocks\"",
			"Germany",
			"Who else can say they hiked a sea of rocks? This hike is filled with ancient Roman works hidden near the trail, and is great fun for those who like to climb.\n\nYou can park your car at the Felsenmeer Informationzentrum, or take a bus into the small town Reichenbach and walk the rest. Parking fees are 3€ for cars and 5€ for buses. Make sure to use the bathroom before the hike, it's 50 cents inside the information center.\n\nFor those who like to follow an organized path you can take the trail head called Siegfriedsquelle (3.2 km) all the way to the top. But the most fun is found climbing the huge boulders to the very top (1.1 km). Sure footedness is definitely required. There are 8 stopping points on the trail, it took my group about 3 hours to reach the very last stop. We were distracted by the insane views, and stopped to eat lunch. When you reach the top don't expect an extravagant opening to view the countryside. There are a couple of openings within the trees to get a good glimpse, but the most beauty is found during the hike itself. It's easier and less stress on the knees to take the Siegfriedsquelle trail on the way down. Round trip this epic journey was about 4.3 km.\n\nIf you're lucky, the beer & wurst hut will be open located about halfway to the top. But no worries there's a restaurant at the top as well!",
			"http://www.felsenmeer-zentrum.de/", "felsenmeer.png", "felsenmeer.jpeg");
	
	private static DestinationBean estrela = new DestinationBean(
			DestinationType.WILD, "Serra da Estrela",
			"Portugal",
			"Fascinating for its natural and cultural history, Parque Natural da Serra da Estrela was one of Portugal’s first designated parks, and at 888 sq km remains the country’s largest protected area. The rugged boulder-strewn meadows and icy lakes of its high country form one of Portugal’s most distinct and unexpected landscapes. It's a glorious, seasonal beauty: the altiplano (upland) area is stunning in the morning or evening light. On the slopes below, rushing rivers historically provided hydro power to spin and weave the Serra’s wool into cloth. Nowadays traditional sheep-herding is giving way to a service economy catering to weekending tourists.",
			"http://www.cm-seia.pt/", "estrela.png", "estrela.jpg");
	
	private static DestinationBean yosemite = new DestinationBean(
			DestinationType.WILD, "Yosemite National Park",
			"USA",
			"The jaw-dropping head-turner of America’s national parks, and a Unesco World Heritage site, Yosemite (yo-sem-it-ee) garners the devotion of all who enter. From the waterfall-striped granite walls buttressing emerald-green Yosemite Valley to the skyscraping giant sequoias catapulting into the air at Mariposa Grove, the place inspires a sense of awe and reverence – four million visitors wend their way to the country’s third-oldest national park annually. But lift your eyes above the crowds and you’ll feel your heart instantly moved by unrivalled splendors: the haughty profile of Half Dome, the hulking presence of El Capitan, the drenching mists of Yosemite Falls, the gemstone lakes of the high country’s subalpine wilderness and Hetch Hetchy’s pristine pathways.",
			"http://www.yosemite.com/", "yosemite.jpg", "yosemite.jpg");
	
	private static DestinationBean forest = new DestinationBean(
			DestinationType.WILD, "Forest Bathing",
			"Japan",
			"A forest bathing trip involves visiting a forest for relaxation and recreation while breathing in volatile substances, called phytoncides (wood essential oils), which are antimicrobial volatile organic compounds derived from trees, such as α-Pinene and limonene. Incorporating forest bathing trips into a good lifestyle was first proposed in 1982 by the Forest Agency of Japan. It has now become a recognized relaxation and/or stress management activity in Japan.",
			"http://www.shinrin-yoku.org/", "forest.jpg", "forest.jpg");
	
	
	public static DestinationBean[] destinations = { london, lesarc, forest, lisbon, laplagne, yosemite, reykjavik, les2alpes, estrela, stockholm, valdisere, felsenmeer, sanfrancisco, 
													 tignes, plitvice, cusco, kopaonik, caminito, berlin, jahorina, covo, tokyo, bansko, cabo, marrakesh, nassfeld, rocha, belgrade, mayrhofen, benagil 
												   };

	// Base group of users
	private static UserBean jane 	= new UserBean("jane", "endava123", "Jane", "Doe", "jane.png");
	private static UserBean lilly 	= new UserBean("lilly", "endava123", "Lilly", "Flower", "lilly.png");
	private static UserBean lisa 	= new UserBean("lisa", "endava123", "Lisa", "Simpson", "lisa.png");
	private static UserBean nancy 	= new UserBean("nancy", "endava123", "Nancy", "Smith", "nancy.png");
	private static UserBean joe 	= new UserBean("joe", "endava123", "Joe", "Johnson", "joe.png");
	private static UserBean mark 	= new UserBean("mark", "endava123", "Mark", "Peterson", "mark.png");
	private static UserBean james 	= new UserBean("james", "endava123", "James", "Williams", "james.png");
	private static UserBean robert 	= new UserBean("robert", "endava123", "Robert", "Jones", "robert.png");

	public static UserBean[] users = { jane, joe, lilly, lisa, mark, nancy, james, robert };
	
	private static ListBean list1 = new ListBean(lesarc, 		ListType.LIKES, 	Arrays.asList(jane.getUsername(), joe.getUsername(), robert.getUsername()));
	private static ListBean list2 = new ListBean(kopaonik, 		ListType.LIKES, 	Arrays.asList(jane.getUsername(), mark.getUsername(), lilly.getUsername()));
	private static ListBean list3 = new ListBean(jahorina,	 	ListType.LIKES, 	Arrays.asList(nancy.getUsername(), lisa.getUsername(), robert.getUsername()));
	private static ListBean list4 = new ListBean(london, 		ListType.LIKES, 	Arrays.asList(james.getUsername(), lisa.getUsername(), jane.getUsername()));
	private static ListBean list5 = new ListBean(caminito,	 	ListType.LIKES, 	Arrays.asList(nancy.getUsername(), lisa.getUsername(), robert.getUsername()));
	private static ListBean list6 = new ListBean(forest, 		ListType.LIKES, 	Arrays.asList(nancy.getUsername(), mark.getUsername(), james.getUsername()));
	private static ListBean list7 = new ListBean(belgrade,	 	ListType.LIKES, 	Arrays.asList(jane.getUsername(), lisa.getUsername(), robert.getUsername()));
	private static ListBean list8 = new ListBean(berlin, 		ListType.LIKES, 	Arrays.asList(nancy.getUsername(), joe.getUsername(), jane.getUsername()));
	private static ListBean list9 = new ListBean(caminito,	 	ListType.LIKES, 	Arrays.asList(robert.getUsername(), lisa.getUsername(), james.getUsername()));
	private static ListBean list10 = new ListBean(nassfeld, 	ListType.LIKES, 	Arrays.asList(mark.getUsername(), joe.getUsername(), robert.getUsername()));
	
	private static ListBean list11 = new ListBean(tignes, 		ListType.WISHLIST, 	Arrays.asList(jane.getUsername(), joe.getUsername(), robert.getUsername()));
	private static ListBean list12 = new ListBean(yosemite, 	ListType.WISHLIST, 	Arrays.asList(lilly.getUsername(), mark.getUsername(), james.getUsername()));
	private static ListBean list13 = new ListBean(cabo, 		ListType.WISHLIST, 	Arrays.asList(lisa.getUsername(), james.getUsername(), joe.getUsername()));
	private static ListBean list14 = new ListBean(benagil, 		ListType.WISHLIST, 	Arrays.asList(nancy.getUsername(), joe.getUsername(), mark.getUsername()));
	private static ListBean list15 = new ListBean(lisbon, 		ListType.WISHLIST, 	Arrays.asList(joe.getUsername(), jane.getUsername(), robert.getUsername()));
	private static ListBean list16 = new ListBean(stockholm,	ListType.WISHLIST, 	Arrays.asList(mark.getUsername(), lilly.getUsername(), james.getUsername()));
	private static ListBean list17 = new ListBean(felsenmeer, 	ListType.WISHLIST, 	Arrays.asList(james.getUsername(), lisa.getUsername(), joe.getUsername()));
	private static ListBean list18 = new ListBean(sanfrancisco, ListType.WISHLIST, 	Arrays.asList(robert.getUsername(), nancy.getUsername(), mark.getUsername()));
	private static ListBean list19 = new ListBean(plitvice, 	ListType.WISHLIST, 	Arrays.asList(jane.getUsername(), joe.getUsername(), robert.getUsername()));
	private static ListBean list20 = new ListBean(cusco, 		ListType.WISHLIST, 	Arrays.asList(lilly.getUsername(), mark.getUsername(), james.getUsername()));

	public static ListBean[] lists = { 	list1, list2, list3, list4, list5, list6, list7, list8, list9, list10,
										list11, list12, list13, list14, list15, list16, list17, list18, list19, list20};
	
}
