package com.example.data.seed

import com.example.data.model.*

object LessonRepositoryData {

    private val LESSONS_MAP = mapOf(
        "phase2_lesson1_landlord" to FullLessonContent(
            id = "phase2_lesson1_landlord",
            phase = PhaseType.PHASE_2,
            categoryIcon = "🏠",
            titleEn = "Talking to Landlord: Reporting a Leak",
            titleFa = "گفتگو با صاحب‌خانه: گزارش نشتی و خرابی",
            estimatedMinutes = 25,
            contextSituationEn = "You recently moved into a rented apartment abroad. This morning you noticed water leaking from the pipe under the kitchen sink, dripping onto the cabinet floor.",
            contextSituationFa = "به تازگی به یک آپارتمان اجاره‌ای در خارج از کشور نقل مکان کرده‌اید. امروز صبح متوجه شدید که لوله زیر سینک آشپزخانه نشتی دارد و آب روی کف کابینت می‌چکد.",
            immigrantMission = "Contact your landlord (Mr. Henderson), explain the urgency clearly, request an urgent plumber visit, and confirm when maintenance can enter.",
            listeningDialogue = listOf(
                DialogueLine(
                    speaker = "Tenant (You)",
                    speakerRole = "Tenant",
                    textEn = "Hello Mr. Henderson, this is Ali from apartment 4B. I'm calling because there's an urgent issue with the plumbing in my kitchen.",
                    textFa = "سلام آقای هندرسون، من علی از آپارتمان ۴B هستم. تماس می‌گیرم چون یک مشکل فوری در لوله‌کشی آشپزخانه‌ام پیش آمده است."
                ),
                DialogueLine(
                    speaker = "Landlord (Mr. Henderson)",
                    speakerRole = "Landlord",
                    textEn = "Hello Ali. Thanks for calling. What seems to be the problem exactly?",
                    textFa = "سلام علی. ممنون از تماست. دقیقاً مشکل چی هست؟"
                ),
                DialogueLine(
                    speaker = "Tenant (You)",
                    speakerRole = "Tenant",
                    textEn = "The pipe under the kitchen sink is leaking heavily. Water is pooling inside the cabinet, so I placed a bucket underneath. Could you send a plumber today?",
                    textFa = "لوله زیر سینک آشپزخانه شدیداً نشتی دارد. آب داخل کابینت جمع شده است، بنابراین یک سطل زیر آن گذاشتم. آیا ممکن است امروز یک لوله‌کش بفرستید؟"
                ),
                DialogueLine(
                    speaker = "Landlord (Mr. Henderson)",
                    speakerRole = "Landlord",
                    textEn = "I appreciate you putting a bucket there. I'll contact our maintenance technician right away. Will someone be home this afternoon around 2 PM?",
                    textFa = "ممنونم که یک سطل آنجا گذاشتید. من بلافاصله با تکنسین تعمیرات‌مان تماس می‌گیرم. آیا بعدازظهر حدود ساعت ۲ کسی در خانه هست؟"
                ),
                DialogueLine(
                    speaker = "Tenant (You)",
                    speakerRole = "Tenant",
                    textEn = "Yes, I will be home all afternoon. If you need entry permission while I'm away, please let me know by text.",
                    textFa = "بله، من تمام بعدازظهر خانه خواهم بود. اگر در غیاب من نیاز به اجازه ورود دارید، لطفاً پیامکی به من اطلاع دهید."
                ),
                DialogueLine(
                    speaker = "Landlord (Mr. Henderson)",
                    speakerRole = "Landlord",
                    textEn = "Perfect. The technician will call you 15 minutes before arrival. Keep the main valve turned off if the dripping worsens.",
                    textFa = "عالیه. تکنسین ۱۵ دقیقه قبل از رسیدن با شما تماس خواهد گرفت. اگر چکه کردن بدتر شد فلکه اصلی را ببندید."
                )
            ),
            comprehensionQuestions = listOf(
                ComprehensionQuestion(
                    id = 1,
                    question = "Why is the tenant calling the landlord urgently?",
                    questionFa = "چرا مستاجر با فوریت با صاحب‌خانه تماس گرفته است؟",
                    options = listOf(
                        "To request a rent reduction",
                        "Because the pipe under the kitchen sink is leaking water",
                        "To ask about parking spaces",
                        "To renew the lease contract"
                    ),
                    correctIndex = 1,
                    explanation = "The tenant specifically calls to report a heavy water leak under the kitchen sink."
                ),
                ComprehensionQuestion(
                    id = 2,
                    question = "What temporary action did the tenant take to prevent damage?",
                    questionFa = "مستاجر چه اقدام موقتی برای جلوگیری از آسیب انجام داد؟",
                    options = listOf(
                        "Turned off electricity in the whole building",
                        "Placed a bucket under the leak",
                        "Left the apartment",
                        "Called emergency 911"
                    ),
                    correctIndex = 1,
                    explanation = "The tenant placed a bucket underneath the leaking pipe to catch the dripping water."
                ),
                ComprehensionQuestion(
                    id = 3,
                    question = "What time did the landlord propose for the maintenance technician visit?",
                    questionFa = "صاحب‌خانه چه ساعتی را برای مراجعه تکنسین پیشنهاد داد؟",
                    options = listOf(
                        "Tomorrow morning at 8 AM",
                        "Today around 2 PM",
                        "Next week on Monday",
                        "Tonight at 10 PM"
                    ),
                    correctIndex = 1,
                    explanation = "The landlord asked if someone would be home around 2 PM this afternoon."
                )
            ),
            vocabularyList = listOf(
                VocabWord(
                    word = "leak",
                    pronunciation = "/liːk/",
                    partOfSpeech = "verb / noun",
                    meaningEn = "To allow liquid or gas to escape through a crack or hole.",
                    meaningFa = "نشتی دادن / چکه کردن / نشتی",
                    exampleEn = "The faucet has a steady leak.",
                    exampleFa = "شیر آب یک نشتی مداوم دارد.",
                    collocation = "water leak, pipe leak"
                ),
                VocabWord(
                    word = "plumber",
                    pronunciation = "/ˈplʌm.ər/ (silent b!)",
                    partOfSpeech = "noun",
                    meaningEn = "A person whose job is to fit and repair pipes and water systems.",
                    meaningFa = "لوله‌کش (توجه: حرف b خوانده نمی‌شود)",
                    exampleEn = "We need an emergency plumber right away.",
                    exampleFa = "ما فوراً به یک لوله‌کش اورژانسی نیاز داریم.",
                    collocation = "call a plumber, hire a licensed plumber"
                ),
                VocabWord(
                    word = "maintenance",
                    pronunciation = "/ˈmeɪn.tən.əns/",
                    partOfSpeech = "noun",
                    meaningEn = "The work needed to keep accommodation and appliances in good working order.",
                    meaningFa = "تعمیرات و نگهداری ساختمان",
                    exampleEn = "Please submit a maintenance request online.",
                    exampleFa = "لطفاً یک درخواست تعمیرات در سایت ثبت کنید.",
                    collocation = "maintenance technician, routine maintenance"
                ),
                VocabWord(
                    word = "shut-off valve",
                    pronunciation = "/ˈʃʌt.ɒf vælv/",
                    partOfSpeech = "noun",
                    meaningEn = "A valve used to stop the flow of water in a pipe in emergency.",
                    meaningFa = "فلکه قطع اضطراری آب",
                    exampleEn = "The shut-off valve is located right below the sink basin.",
                    exampleFa = "فلکه قطع آب دقیقاً زیر کاسه سینک قرار دارد.",
                    collocation = "turn off the shut-off valve"
                ),
                VocabWord(
                    word = "pooling",
                    pronunciation = "/ˈpuː.lɪŋ/",
                    partOfSpeech = "verb / adjective",
                    meaningEn = "Collecting into a puddle of liquid on a flat surface.",
                    meaningFa = "جمع شدن آب به صورت حوضچه یا تاول روی زمین",
                    exampleEn = "Water is pooling under the refrigerator.",
                    exampleFa = "آب زیر یخچال جمع شده و حوضچه درست کرده است.",
                    collocation = "water pooling on the floor"
                )
            ),
            grammarInContext = GrammarPoint(
                topic = "Present Continuous vs Present Perfect for Ongoing Problems",
                topicFa = "حال استمراری و ماضی نقلی برای گزارش مشکلات جاری خانه",
                coreRule = "Use Present Continuous (is leaking) for what is happening right now. Use Present Perfect (has been leaking) to emphasize duration.",
                whyItMattersForMigration = "Landlords and emergency dispatchers need to understand if the crisis started 5 minutes ago or 3 days ago for insurance claims and liability.",
                whyItMattersFa = "صاحب‌خانه‌ها و شرکت‌های بیمه در خارج از کشور نیاز به درک دقیق زمان وقوع خرابی دارند تا مسئولیت خسارت مشخص شود.",
                examples = listOf(
                    "The kitchen pipe is leaking." to "لوله آشپزخانه در حال حاضر نشتی دارد.",
                    "The water has been dripping since yesterday." to "آب از دیروز تا الان در حال چکه کردن بوده است.",
                    "I have placed a bucket under the cabinet." to "یک سطل زیر کابینت گذاشته‌ام."
                ),
                commonPersianMistake = "Persian speakers often say: 'The pipe leaks from two days ago' or 'I have leak in bathroom.'",
                correctForm = "Say: 'The pipe has been leaking for two days' or 'There is a leak in my bathroom.'"
            ),
            speakingPractices = listOf(
                SpeakingTask(
                    promptEn = "Say aloud: Introduce yourself and report the urgent water issue to the landlord.",
                    promptFa = "با صدای بلند بگویید: خودتان را معرفی کنید و مشکل فوری آب را گزارش دهید.",
                    targetPhrase = "Hello, this is Ali from apartment 4B. There is a water leak under my kitchen sink.",
                    targetPhraseFa = "سلام، من علی از واحد ۴B هستم. یک نشتی آب زیر سینک آشپزخانه‌ام وجود دارد.",
                    sampleAnswer = "Hello, this is Ali from unit 4B. I am reporting an urgent leak under the kitchen sink.",
                    pronunciationTips = "Stress 'urgent' on the first syllable (/UR-jent/) and keep 'plumber' with a silent 'b'."
                ),
                SpeakingTask(
                    promptEn = "Say aloud: Ask politely if a maintenance technician can visit this afternoon.",
                    promptFa = "با صدای بلند بگویید: محترمانه بپرسید آیا تکنسین می‌تواند بعدازظهر مراجعه کند.",
                    targetPhrase = "Could you please send someone to inspect it this afternoon?",
                    targetPhraseFa = "آیا ممکن است لطفاً کسی را برای بررسی آن در بعدازظهر امروز بفرستید؟",
                    sampleAnswer = "Could you please arrange for a technician to check it this afternoon?",
                    pronunciationTips = "Link 'Could you' smoothly (/kʊdʒuː/) for natural native flow."
                )
            ),
            readingDocument = ReadingMaterial(
                type = "Residential Tenancy Agreement — Section 14 (Repairs & Maintenance)",
                headline = "Standard Tenancy Clause 14.2: Emergency Maintenance Notification",
                headlineFa = "بند ۱۴.۲ قرارداد اجاره مسکونی: اطلاع‌رسانی تعمیرات اضطراری",
                content = """
RESIDENTIAL TENANCIES ACT - SECTION 14.2:
The Tenant shall immediately notify the Landlord in writing or by telephone of any water leak, plumbing failure, heating malfunction, or electrical hazard. In case of water leakage:
1. The Tenant must make reasonable efforts to mitigate water damage (e.g., placing containers, wiping excess water).
2. The Landlord or authorized contractor may enter the rental unit on emergency grounds with 2-hour notice or immediately if imminent structural damage is threatened.
3. Routine maintenance requests require a minimum 24-hour written notice prior to landlord entry.
                """.trimIndent(),
                keyTakeawaysEn = listOf(
                    "You must report leaks immediately to avoid liability for flooring damage.",
                    "Placing a bucket/towel is considered a legal requirement to 'mitigate damage'.",
                    "Emergency repairs allow faster landlord entry compared to routine 24h notice."
                ),
                keyTakeawaysFa = listOf(
                    "باید نشتی را سریع گزارش کنید تا مسئول خسارت وارده به کف‌پوش شناخته نشوید.",
                    "قرار دادن سطل یا حوله یک اقدام قانونی الزامی برای کاهش خسارت (Mitigate) است.",
                    "تعمیرات اضطراری به صاحب‌خانه اجازه می‌دهد سریع‌تر از موعد ۲۴ ساعته عادی وارد شود."
                )
            ),
            writingTask = WritingTask(
                promptEn = "Write an urgent text message / email to your landlord describing the leak and asking for a technician visit today.",
                promptFa = "یک پیامک یا ایمیل رسمی و فوری به صاحب‌خانه بنویسید، نشتی را توضیح دهید و درخواست بازدید تعمیرکار کنید.",
                contextDescription = "You need a written record so that if water causes floor damage, you have proof you notified them promptly.",
                starterText = "Hi Mr. Henderson, I am writing to let you know that...",
                requiredKeywords = listOf("leak", "kitchen sink", "bucket", "urgent", "technician"),
                idealModelAnswer = "Hi Mr. Henderson, I am writing to report an urgent leak under the kitchen sink in Apt 4B. Water has been dripping into the cabinet since this morning. I placed a bucket to prevent damage. Could you please send a maintenance technician today? I will be home after 1:00 PM. Thank you, Ali."
            ),
            finalChallenge = SimulationChallenge(
                scenarioTitle = "Live Simulation: Landlord Emergency Call",
                partnerRole = "Landlord (Mr. Henderson)",
                partnerOpeningLine = "Henderson Properties, this is Mark Henderson speaking. How can I help you today?",
                missionObjectiveEn = "Explain the leak in your apartment, mention what preventative steps you took, and schedule a repair time.",
                missionObjectiveFa = "نشتی آپارتمان را شرح دهید، اقدامات پیشگیرانه خود را ذکر کنید و زمان مراجعه تعمیرکار را هماهنگ کنید.",
                expectedKeywords = listOf("leak", "sink", "bucket", "urgent", "today")
            )
        ),

        "phase3_lesson1_firstday" to FullLessonContent(
            id = "phase3_lesson1_firstday",
            phase = PhaseType.PHASE_3,
            categoryIcon = "💼",
            titleEn = "First Day at Work & Team Intro",
            titleFa = "روز اول کاری و معرفی حرفه‌ای به تیم",
            estimatedMinutes = 25,
            contextSituationEn = "Today is your very first day working at a tech & consulting firm abroad. Your manager Sarah is taking you around the office floor to introduce you to your new colleagues.",
            contextSituationFa = "امروز اولین روز کاری شما در یک شرکت بین‌المللی است. مدیر شما سارا شما را در بخش‌های مختلف شرکت می‌گرداند تا شما را به همکاران جدیدتان معرفی کند.",
            immigrantMission = "Introduce yourself professionally, briefly explain your background, ask how the team coordinates tasks, and establish friendly workplace rapport.",
            listeningDialogue = listOf(
                DialogueLine(
                    speaker = "Manager (Sarah)",
                    speakerRole = "Manager",
                    textEn = "Everyone, may I have your attention for a moment? I'd like to introduce Ali, who is joining us today as our new software engineer.",
                    textFa = "همگی، ممکن است یک لحظه توجه کنید؟ مایلم علی را معرفی کنم که از امروز به عنوان مهندس نرم‌افزار جدید به جمع ما می‌پیوندد."
                ),
                DialogueLine(
                    speaker = "Ali (You)",
                    speakerRole = "New Employee",
                    textEn = "Hi everyone! It's a pleasure to meet you all. I'm really excited to join the team and collaborate on upcoming projects.",
                    textFa = "سلام به همگی! از آشنایی با همه شما بسیار خوشحالم. خیلی مشتاقم که به تیم ملحق شوم و در پروژه‌های آینده همکاری کنم."
                ),
                DialogueLine(
                    speaker = "Colleague (David)",
                    speakerRole = "Senior Colleague",
                    textEn = "Welcome aboard, Ali! Where did you work before joining us?",
                    textFa = "به جمع ما خوش آمدی علی! قبل از پیوستن به ما کجا کار می‌کردی؟"
                ),
                DialogueLine(
                    speaker = "Ali (You)",
                    speakerRole = "New Employee",
                    textEn = "I worked on cloud systems and backend infrastructure for about five years before moving here. I'm looking forward to getting up to speed with your workflow.",
                    textFa = "من حدود پنج سال قبل از مهاجرت روی سیستم‌های ابری و زیرساخت بک‌اند کار کردم. مشتاقم هر چه سریع‌تر با روال کاری شما هماهنگ شوم."
                ),
                DialogueLine(
                    speaker = "Colleague (Elena)",
                    speakerRole = "Product Lead",
                    textEn = "That's fantastic. We have our daily standup meeting at 9:30 AM every morning on Slack and Zoom. Let me know if you need help with onboarding setup.",
                    textFa = "فوق‌العاده است. ما هر روز صبح ساعت ۹:۳۰ جلسه کوتاه (استندآپ) روی اسلک و زوم داریم. اگر در راه‌اندازی و مراحل ورود به شرکت کمکی خواستی خبرم کن."
                )
            ),
            comprehensionQuestions = listOf(
                ComprehensionQuestion(
                    id = 1,
                    question = "What role is Ali taking on in the company?",
                    questionFa = "علی چه نقشی را در شرکت به عهده می‌گیرد؟",
                    options = listOf("Marketing director", "Software engineer", "Office administrator", "Financial auditor"),
                    correctIndex = 1,
                    explanation = "Sarah introduces Ali as the new software engineer joining the team."
                ),
                ComprehensionQuestion(
                    id = 2,
                    question = "What time is the team's daily standup meeting?",
                    questionFa = "جلسه روزانه استندآپ تیم چه ساعتی برگزار می‌شود؟",
                    options = listOf("8:00 AM", "9:30 AM", "1:00 PM", "5:00 PM"),
                    correctIndex = 1,
                    explanation = "Elena mentions their daily standup meeting takes place at 9:30 AM every morning."
                )
            ),
            vocabularyList = listOf(
                VocabWord(
                    word = "welcome aboard",
                    pronunciation = "/ˈwel.kəm əˈbɔːrd/",
                    partOfSpeech = "phrase",
                    meaningEn = "A warm greeting used to welcome a new member to an organization or team.",
                    meaningFa = "به جمع ما خوش آمدی / ورودت را به شرکت تبریک می‌گویم",
                    exampleEn = "Welcome aboard, Ali! We're glad to have you with us.",
                    exampleFa = "ورودت را تبریک می‌گویم علی! از بودنت در جمع‌مان خوشحالیم.",
                    collocation = "welcome aboard the team"
                ),
                VocabWord(
                    word = "get up to speed",
                    pronunciation = "/ɡet ʌp tuː spiːd/",
                    partOfSpeech = "idiom",
                    meaningEn = "To learn the necessary information or skills to reach standard efficiency.",
                    meaningFa = "مسلط شدن و هماهنگ شدن سریع با روال کار",
                    exampleEn = "It usually takes two weeks for new hires to get up to speed.",
                    exampleFa = "معمولاً دو هفته طول می‌کشد تا نیروهای جدید با روال کار مسلط شوند.",
                    collocation = "get up to speed on the project"
                ),
                VocabWord(
                    word = "collaborate",
                    pronunciation = "/kəˈlæb.ə.reɪt/",
                    partOfSpeech = "verb",
                    meaningEn = "To work jointly with others on an activity or project.",
                    meaningFa = "همکاری نزدیک داشتن / مشارکت تیمی",
                    exampleEn = "We collaborate across design, engineering, and product.",
                    exampleFa = "ما در بخش‌های طراحی، مهندسی و محصول با یکدیگر همکاری می‌کنیم.",
                    collocation = "collaborate with colleagues, collaborate on a project"
                ),
                VocabWord(
                    word = "onboarding",
                    pronunciation = "/ˈɑːnˌbɔːr.dɪŋ/",
                    partOfSpeech = "noun",
                    meaningEn = "The process of integrating a new employee into an organization.",
                    meaningFa = "فرایند پذیرش، آشنایی و آموزش اولیه نیروی جدید در شرکت",
                    exampleEn = "My manager scheduled three onboarding sessions for this week.",
                    exampleFa = "مدیرم سه جلسه آشنایی و آموزش برای این هفته برنامه‌ریزی کرد.",
                    collocation = "onboarding process, onboarding documentation"
                )
            ),
            grammarInContext = GrammarPoint(
                topic = "Present Perfect with 'For' & 'Since' for Career Background",
                topicFa = "ماضی نقلی برای بیان سابقه کاری و مدت زمان تجربه",
                coreRule = "Use Present Perfect (I have worked / I have been working) with 'for [duration]' and 'since [start point]'.",
                whyItMattersForMigration = "In Western workplaces, using Present Simple ('I work 5 years in IT') confuses colleagues because it sounds like you are currently doing a 5-year project rather than having 5 years of accumulated expertise.",
                whyItMattersFa = "در مصاحبه‌ها و معرفی‌های کاری، عدم استفاده از Present Perfect سابقه کاری شما را مبهم و غیردقیق نشان می‌دهد.",
                examples = listOf(
                    "I have worked in finance for five years." to "من به مدت پنج سال در حوزه مالی کار کرده‌ام.",
                    "I have been living in Toronto since January." to "من از ماه ژانویه در تورنتو زندگی می‌کنم."
                ),
                commonPersianMistake = "Saying: 'I have 5 years experience from Iran' or 'I work in IT from 2018.'",
                correctForm = "Say: 'I have five years of experience in IT' or 'I have been working in IT since 2018.'"
            ),
            speakingPractices = listOf(
                SpeakingTask(
                    promptEn = "Say aloud: Introduce yourself to your new team at the first morning meeting.",
                    promptFa = "با صدای بلند بگویید: در اولین جلسه صبحگاهی خود را به همکاران معرفی کنید.",
                    targetPhrase = "Hi everyone, I'm Ali. I'm really excited to join the team and look forward to working with you all.",
                    targetPhraseFa = "سلام به همگی، من علی هستم. خیلی خوشحالم که به تیم پیوستم و مشتاق همکاری با همه شما هستم.",
                    sampleAnswer = "Hi team, I am Ali. I just joined as a software developer and I look forward to collaborating with all of you.",
                    pronunciationTips = "Smile while speaking—native speakers perceive warm intonation as a sign of openness and professionalism."
                )
            ),
            readingDocument = ReadingMaterial(
                type = "Company Slack / Email Welcome Announcement",
                headline = "Welcome our newest team member: Ali Rezaei",
                headlineFa = "ایمیل خوش‌آمدگویی شرکت به همکاران",
                content = """
Subject: Welcome Ali to the Engineering Team! 🚀

Hi Team,

Please join us in welcoming Ali Rezaei, who joins us today as Senior Software Engineer. Ali brings extensive experience in backend systems and distributed cloud architectures.

Ali will be paired with Elena for his first two weeks of onboarding. Feel free to drop by his desk or send a warm hello on Slack (#engineering-general)!

Welcome aboard, Ali!

Best,
Sarah Jenkins
VP of Engineering
                """.trimIndent(),
                keyTakeawaysEn = listOf(
                    "In Western offices, onboarding buddies ('paired with Elena') help you learn culture & tools.",
                    "Slack channels like #engineering-general are standard places for quick informal greetings."
                ),
                keyTakeawaysFa = listOf(
                    "در شرکت‌های خارجی یک همکار راهنما (Buddy) برای دو هفته اول به شما اختصاص داده می‌شود.",
                    "کانال‌های اسلک شرکت برای خوش‌آمدگویی‌های غیررسمی و ارتباط با همکاران استفاده می‌شود."
                )
            ),
            writingTask = WritingTask(
                promptEn = "Write a short reply to the team's welcome message on Slack, expressing thanks and openness to connect.",
                promptFa = "یک پاسخ کوتاه به پیام خوش‌آمدگویی تیم در اسلک بنویسید و از استقبال‌شان تشکر کنید.",
                contextDescription = "Workplace Slack messages should be polite, concise, and approachable.",
                starterText = "Thank you so much Sarah and everyone! I am really looking forward to...",
                requiredKeywords = listOf("thank you", "excited", "looking forward", "collaborate", "team"),
                idealModelAnswer = "Thank you so much Sarah and everyone for the warm welcome! I'm really excited to join the team and look forward to collaborating with all of you. Excited to get started!"
            ),
            finalChallenge = SimulationChallenge(
                scenarioTitle = "Live Simulation: Watercooler Introduction with Elena",
                partnerRole = "Product Lead (Elena)",
                partnerOpeningLine = "Hey Ali, great to have you on the floor! Getting settled into your workstation okay?",
                missionObjectiveEn = "Respond warmly, confirm your setup is going well, and ask one question about team communication tools.",
                missionObjectiveFa = "با خوشرویی پاسخ دهید، تایید کنید سیستم‌تان آماده است و یک سوال در مورد ابزارهای ارتباطی بپرسید.",
                expectedKeywords = listOf("thanks", "settled", "setup", "slack", "standup")
            )
        ),

        "phase2_lesson3_doctor" to FullLessonContent(
            id = "phase2_lesson3_doctor",
            phase = PhaseType.PHASE_2,
            categoryIcon = "🏥",
            titleEn = "Clinic Visit: Describing Symptoms",
            titleFa = "ویزیت پزشک: شرح دقیق علائم بیماری",
            estimatedMinutes = 25,
            contextSituationEn = "You have had a persistent sore throat, mild fever, and dry cough for three days. You are at the local walk-in clinic speaking directly to the doctor.",
            contextSituationFa = "سه روز است که گلودرد مداوم، تب خفیف و سرفه خشک دارید. در کلینیک عمومی با پزشک صحبت می‌کنید.",
            immigrantMission = "Describe your physical symptoms accurately, state when they started, mention any allergies, and ask about the recommended prescription and dosage.",
            listeningDialogue = listOf(
                DialogueLine(
                    speaker = "Doctor (Dr. Patel)",
                    speakerRole = "Physician",
                    textEn = "Good morning. Come on in and have a seat. What brings you into the clinic today?",
                    textFa = "صبح بخیر. بفرمایید داخل و بنشینید. امروز به چه دلیلی به کلینیک مراجعه کردید؟"
                ),
                DialogueLine(
                    speaker = "Patient (You)",
                    speakerRole = "Patient",
                    textEn = "Good morning doctor. I've had a severe sore throat and a dry cough for the past three days, and last night I developed a mild fever.",
                    textFa = "صبح بخیر دکتر. من در سه روز گذشته گلودرد شدید و سرفه خشک داشته‌ام، و دیشب تب خفیفی پیدا کردم."
                ),
                DialogueLine(
                    speaker = "Doctor (Dr. Patel)",
                    speakerRole = "Physician",
                    textEn = "I see. Let me take a look at your throat and check your temperature. Are you experiencing any shortness of breath or body aches?",
                    textFa = "متوجه شدم. اجازه دهید گلوی شما را معاینه کنم و درجه حرارت را بسنجم. آیا تنگی نفس یا درد عضلانی هم دارید؟"
                ),
                DialogueLine(
                    speaker = "Patient (You)",
                    speakerRole = "Patient",
                    textEn = "No shortness of breath, but I have a constant dull headache and slight fatigue. Are there any antibiotics or lozenges you recommend?",
                    textFa = "تنگی نفس ندارم، اما سردرد مبهم مداوم و کمی خستگی دارم. آیا آنتی‌بیوتیک یا قرص مکیدنی خاصی توصیه می‌کنید؟"
                ),
                DialogueLine(
                    speaker = "Doctor (Dr. Patel)",
                    speakerRole = "Physician",
                    textEn = "Your throat is inflamed, likely a viral pharyngitis. Antibiotics won't help with viral infections, but I will prescribe an anti-inflammatory spray and recommend acetaminophen for the fever.",
                    textFa = "گلوی شما ملتهب است، به احتمال زیاد یک فارنژیت ویروسی است. آنتی‌بیوتیک روی عفونت ویروسی اثری ندارد، اما من یک اسپری ضدالتهاب می‌نویسم و استامینوفن را برای تب پیشنهاد می‌کنم."
                )
            ),
            comprehensionQuestions = listOf(
                ComprehensionQuestion(
                    id = 1,
                    question = "Why does the doctor decide NOT to prescribe antibiotics?",
                    questionFa = "چرا پزشک تصمیم گرفت آنتی‌بیوتیک تجویز نکند؟",
                    options = listOf(
                        "The pharmacy is closed",
                        "The patient is allergic to all medicines",
                        "The inflammation is likely viral, and antibiotics only treat bacterial infections",
                        "The patient did not bring insurance"
                    ),
                    correctIndex = 2,
                    explanation = "The doctor explains that antibiotics are ineffective against viral throat infections."
                )
            ),
            vocabularyList = listOf(
                VocabWord(
                    word = "symptom",
                    pronunciation = "/ˈsɪmp.təm/",
                    partOfSpeech = "noun",
                    meaningEn = "A physical or mental feature indicating a condition of disease.",
                    meaningFa = "نشانه یا علائم بیماری",
                    exampleEn = "What symptoms have you noticed since Thursday?",
                    exampleFa = "از روز پنجشنبه چه علائمی مشاهده کرده‌اید؟",
                    collocation = "flu symptoms, mild symptoms"
                ),
                VocabWord(
                    word = "prescribe",
                    pronunciation = "/prɪˈskraɪb/",
                    partOfSpeech = "verb",
                    meaningEn = "To advise and authorize the use of a medicine or treatment for someone in writing.",
                    meaningFa = "تجویز کردن دارو توسط پزشک",
                    exampleEn = "The physician prescribed an inhaler for my cough.",
                    exampleFa = "پزشک برای سرفه‌ام یک اسپری تنفسی تجویز کرد.",
                    collocation = "prescribe medication, prescribe dosage"
                ),
                VocabWord(
                    word = "dull headache",
                    pronunciation = "/dʌl ˈhed.eɪk/",
                    partOfSpeech = "phrase",
                    meaningEn = "A continuous, mild-to-moderate aching pain in the head (not sharp).",
                    meaningFa = "سردرد مبهم و مداوم (نه تیز و ناگهانی)",
                    exampleEn = "I have had a dull headache since yesterday morning.",
                    exampleFa = "از دیروز صبح سردرد مبهم و ممتدی دارم.",
                    collocation = "dull ache, sharp pain"
                )
            ),
            grammarInContext = GrammarPoint(
                topic = "Describing Pain & Duration with Present Perfect",
                topicFa = "شرح درد و مدت زمان علائم به پزشک",
                coreRule = "Use: 'I have had [symptom] for [X days]' NOT 'I have pain from X days.'",
                whyItMattersForMigration = "Medical staff in ER and walk-in clinics prioritize triage urgency strictly by exact duration and onset patterns.",
                whyItMattersFa = "کادر درمانی در تریاژ بیمارستان‌ها اولویت ویزیت را بر اساس زمان‌بندی دقیق علائم شما تعیین می‌کنند.",
                examples = listOf(
                    "I have had a fever for three days." to "سه روز است که تب دارم.",
                    "My throat started hurting on Tuesday." to "گلویم از روز سه‌شنبه شروع به درد کرد."
                ),
                commonPersianMistake = "Persian speakers say: 'I have pain in stomach from two weeks.'",
                correctForm = "Say: 'I've had stomach pain for the past two weeks.'"
            ),
            speakingPractices = listOf(
                SpeakingTask(
                    promptEn = "Say aloud: Tell the doctor about your symptoms and how long you've had them.",
                    promptFa = "با صدای بلند بگویید: به پزشک بگویید چه علائمی دارید و چند روز است که شروع شده است.",
                    targetPhrase = "Doctor, I've had a sore throat and fever for the past three days.",
                    targetPhraseFa = "دکتر، من در سه روز گذشته گلودرد و تب داشته‌ام.",
                    sampleAnswer = "Doctor, I have had a sore throat and mild fever for three days.",
                    pronunciationTips = "Pronounce 'past' and 'three' with a clear /θ/ sound for 'three' (tongue between teeth)."
                )
            ),
            readingDocument = ReadingMaterial(
                type = "Clinic Prescription & Aftercare Leaflet",
                headline = "Patient Care Instructions: Viral Pharyngitis Treatment",
                headlineFa = "دستورالعمل مراقبت بعد از ویزیت برای گلودرد ویروسی",
                content = """
AFTERCARE INSTRUCTIONS:
- Medication: Acetaminophen 500mg every 6 hours as needed for fever and throat discomfort. Do not exceed 3,000mg in 24 hours.
- Hydration: Drink warm fluids (tea with honey) and rest.
- When to Seek Urgent Emergency Care:
  * Difficulty swallowing liquids or breathing.
  * Fever exceeding 39.5°C (103°F) for more than 48 hours.
  * Inability to open mouth fully (trismus).
                """.trimIndent(),
                keyTakeawaysEn = listOf(
                    "Maximum acetaminophen dosage is 3,000mg daily to protect liver health.",
                    "Seek immediate emergency care if breathing or swallowing becomes difficult."
                ),
                keyTakeawaysFa = listOf(
                    "حداکثر مصرف استامینوفن در روز ۳۰۰۰ میلی‌گرم است تا به کبد آسیبی نرسد.",
                    "در صورت بروز تنگی نفس یا اختلال در بلع فوراً به اورژانس مراجعه کنید."
                )
            ),
            writingTask = WritingTask(
                promptEn = "Write an email to your employer/manager explaining you visited the clinic and need to take one sick day off.",
                promptFa = "یک ایمیل به مدیر خود بنویسید و توضیح دهید به کلینیک مراجعه کرده‌اید و نیاز به یک روز مرخصی استعلاجی دارید.",
                contextDescription = "Professional sick leave emails should state inability to work, brief status, and when you plan to return.",
                starterText = "Hi Sarah, I am writing to let you know that I am unwell today...",
                requiredKeywords = listOf("unwell", "doctor", "clinic", "sick leave", "tomorrow"),
                idealModelAnswer = "Hi Sarah, I am writing to let you know that I visited the walk-in clinic this morning due to a fever and throat infection. The doctor advised me to rest today. I will be taking a sick day and expect to be back at work tomorrow. I will monitor my email periodically for urgent items. Best regards, Ali."
            ),
            finalChallenge = SimulationChallenge(
                scenarioTitle = "Live Simulation: Clinic Doctor Consultation",
                partnerRole = "Doctor (Dr. Patel)",
                partnerOpeningLine = "Hello! Please come in. What symptoms are bothering you today?",
                missionObjectiveEn = "Explain your symptoms, state how many days you've been sick, and ask about medication.",
                missionObjectiveFa = "علائم خود را شرح دهید، مدت زمان بیماری را بگویید و در مورد داروی مناسب سوال بپرسید.",
                expectedKeywords = listOf("throat", "fever", "days", "headache", "prescription")
            )
        ),

        "phase2_lesson2_bank" to FullLessonContent(
            id = "phase2_lesson2_bank",
            phase = PhaseType.PHASE_2,
            categoryIcon = "🏦",
            titleEn = "Opening a Bank Account & Debit Card",
            titleFa = "افتتاح حساب بانکی و کارت اعتباری",
            estimatedMinutes = 25,
            contextSituationEn = "You recently arrived in the country and need to open a checking account to receive your salary and pay rent. You are speaking with the bank customer service advisor.",
            contextSituationFa = "به تازگی وارد کشور شده‌اید و برای دریافت حقوق و پرداخت اجاره نیاز به افتتاح حساب جاری دارید. با متصدی خدمات مشتریان بانک صحبت می‌کنید.",
            immigrantMission = "Ask for a checking account without monthly fees, request a debit card, and ask how to set up direct deposit for your salary.",
            listeningDialogue = listOf(
                DialogueLine(
                    speaker = "Bank Advisor (James)",
                    speakerRole = "Bank Advisor",
                    textEn = "Good afternoon, welcome to Metro Bank. How can I assist you today?",
                    textFa = "بعدازظهر بخیر، به مترو بانک خوش آمدید. امروز چطور می‌توانم به شما کمک کنم؟"
                ),
                DialogueLine(
                    speaker = "Customer (You)",
                    speakerRole = "Customer",
                    textEn = "Hello! I recently moved here and I'd like to open a checking account with a debit card for daily expenses.",
                    textFa = "سلام! من به تازگی به اینجا نقل مکان کرده‌ام و مایلم یک حساب جاری همراه با کارت نقدی برای هزینه‌های روزمره افتتاح کنم."
                ),
                DialogueLine(
                    speaker = "Bank Advisor (James)",
                    speakerRole = "Bank Advisor",
                    textEn = "Wonderful. Do you have two pieces of government ID and proof of address, such as your lease contract?",
                    textFa = "بسیار عالی. آیا دو مدرک شناسایی دولتی و گواهی آدرس مانند قرارداد اجاره منزلتان را به همراه دارید؟"
                ),
                DialogueLine(
                    speaker = "Customer (You)",
                    speakerRole = "Customer",
                    textEn = "Yes, I have my passport, work permit, and a signed lease agreement. Are there any monthly maintenance fees?",
                    textFa = "بله، گذرنامه، مجوز کار و قرارداد اجاره امضا شده را همراه دارم. آیا هزینه نگهداری ماهیانه دارد؟"
                ),
                DialogueLine(
                    speaker = "Bank Advisor (James)",
                    speakerRole = "Bank Advisor",
                    textEn = "Our Newcomer Account has zero monthly fees for the first year. We will print your debit card right now and give you a direct deposit form for your employer.",
                    textFa = "حساب ویژه تازه‌واردین ما برای سال اول بدون کارمزد ماهانه است. ما کارت نقدی شما را همین الان صادر می‌کنیم و فرم واریز مستقیم برای کارفرمایتان را به شما تحویل می‌دهیم."
                )
            ),
            comprehensionQuestions = listOf(
                ComprehensionQuestion(
                    id = 1,
                    question = "What documents did the bank advisor require to open the account?",
                    questionFa = "متصدی بانک چه مدارکی را برای افتتاح حساب درخواست کرد؟",
                    options = listOf(
                        "Only cash in advance",
                        "Two pieces of government ID and proof of address",
                        "A letter from university professor",
                        "A local driver's license only"
                    ),
                    correctIndex = 1,
                    explanation = "The bank requires two official identification documents and a proof of address like a lease contract."
                )
            ),
            vocabularyList = listOf(
                VocabWord(
                    word = "checking account",
                    pronunciation = "/ˈtʃek.ɪŋ əˌkaʊnt/",
                    partOfSpeech = "noun",
                    meaningEn = "A bank account from which payments can be withdrawn with checks or debit cards.",
                    meaningFa = "حساب جاری روزمره (برای دریافت حقوق و پرداخت قبوض)",
                    exampleEn = "I use my checking account to pay monthly utility bills.",
                    exampleFa = "من از حساب جاری برای پرداخت قبوض ماهیانه استفاده می‌کنم.",
                    collocation = "open a checking account"
                ),
                VocabWord(
                    word = "direct deposit",
                    pronunciation = "/daɪˈrekt dɪˌpɑː.zɪt/",
                    partOfSpeech = "noun",
                    meaningEn = "The electronic transfer of a payment directly from the payer's account into the recipient's bank account.",
                    meaningFa = "واریز مستقیم حقوق به حساب بانکی بدون چک کاغذی",
                    exampleEn = "My company requires a direct deposit void cheque to process my payroll.",
                    exampleFa = "شرکت من برای پرداخت حقوق به برگه واریز مستقیم نیاز دارد.",
                    collocation = "set up direct deposit"
                )
            ),
            grammarInContext = GrammarPoint(
                topic = "Polite Financial Requests with 'Would like to' & 'Could you'",
                topicFa = "درخواست‌های محترمانه بانکی با would like و could you",
                coreRule = "Use 'I would like to open...' instead of 'I want to open...'. Use 'Could you explain...' instead of 'Explain me...'",
                whyItMattersForMigration = "Polite modal structures make bank tellers and government officers significantly more helpful and cooperative.",
                whyItMattersFa = "لحن محترمانه باعث تسهیل و تسریع انجام امور اداری و بانکی شما در خارج از کشور می‌شود.",
                examples = listOf(
                    "I would like to set up online banking." to "مایلم بانکداری اینترنتی را فعال کنم.",
                    "Could you please print a void cheque?" to "آیا ممکن است یک برگه تاییدیه حساب برای من چاپ کنید؟"
                ),
                commonPersianMistake = "Persian speakers say: 'I want open account' or 'Give me card.'",
                correctForm = "Say: 'I would like to open a new checking account, please.'"
            ),
            speakingPractices = listOf(
                SpeakingTask(
                    promptEn = "Say aloud: Ask the bank teller to open a newcomer checking account.",
                    promptFa = "با صدای بلند بگویید: از کارمند بانک درخواست افتتاح حساب جاری تازه‌واردین کنید.",
                    targetPhrase = "Good afternoon, I would like to open a checking account with direct deposit.",
                    targetPhraseFa = "بعدازظهر بخیر، مایلم یک حساب جاری همراه با واریز مستقیم حقوق افتتاح کنم.",
                    sampleAnswer = "Hi, I would like to open a new checking account for my payroll.",
                    pronunciationTips = "Contract 'I would' into 'I'd' (/aɪd/) for fluent native rhythm."
                )
            ),
            readingDocument = ReadingMaterial(
                type = "Banking Terms & Conditions",
                headline = "Schedule of Banking Fees & Transaction Limits",
                headlineFa = "جدول کارمزدها و سقف تراکنش‌های بانکی",
                content = """
NEWCOMER ADVANTAGE CHECKING:
- Monthly Maintenance Fee: $0 for 12 months ($14.95/month thereafter, waived with $3,000 minimum balance).
- Daily ATM Withdrawal Limit: $1,000 CAD/USD.
- Daily Point-of-Sale (Debit Tap) Limit: $2,500.
- Interac e-Transfers / Wire: Unlimited free domestic e-transfers.
                """.trimIndent(),
                keyTakeawaysEn = listOf(
                    "Monthly fee is waived during the first 12 months for new immigrants.",
                    "Maintaining a $3,000 balance waives fees after the first year."
                ),
                keyTakeawaysFa = listOf(
                    "کارمزد ماهانه برای ۱۲ ماه اول ورود به کشور کاملاً رایگان است.",
                    "با حفظ حداقل موجودی ۳۰۰۰ دلار پس از سال اول نیز کارمزدی کسر نمی‌شود."
                )
            ),
            writingTask = WritingTask(
                promptEn = "Write an email to your company HR / payroll department providing your bank details for direct deposit.",
                promptFa = "یک ایمیل به بخش حقوق و دستمزد شرکت بنویسید و اطلاعات بانکی خود را جهت واریز حقوق ارسال کنید.",
                contextDescription = "Professional payroll emails must include institution number, transit number, and account number.",
                starterText = "Hi Payroll Team, Please find attached my direct deposit details...",
                requiredKeywords = listOf("payroll", "direct deposit", "account", "attached", "salary"),
                idealModelAnswer = "Hi Payroll Team, I hope this email finds you well. Please find attached my direct deposit form and void cheque for my upcoming salary disbursements. Please let me know if you require any additional banking information. Best regards, Ali."
            ),
            finalChallenge = SimulationChallenge(
                scenarioTitle = "Live Simulation: Bank Account Setup",
                partnerRole = "Bank Advisor (James)",
                partnerOpeningLine = "Welcome to Metro Bank! Are you looking to open a personal or business account today?",
                missionObjectiveEn = "State you want a checking account, ask about debit cards, and confirm the fee waiver.",
                missionObjectiveFa = "بگویید حساب شخصی جاری می‌خواهید، در مورد کارت نقدی بپرسید و معافیت کارمزد را تایید کنید.",
                expectedKeywords = listOf("checking", "account", "debit", "deposit", "fees")
            )
        ),

        "phase3_lesson2_standup" to FullLessonContent(
            id = "phase3_lesson2_standup",
            phase = PhaseType.PHASE_3,
            categoryIcon = "📊",
            titleEn = "Daily Standup: Giving Task Updates",
            titleFa = "جلسه کوتاه کاری و ارائه وضعیت تسک‌ها",
            estimatedMinutes = 22,
            contextSituationEn = "Every morning at 9:30 AM, your engineering and product team has a 15-minute standup meeting on Zoom/Slack. It is your turn to speak.",
            contextSituationFa = "هر روز صبح ساعت ۹:۳۰ تیم شما یک جلسه کوتاه ۱۵ دقیقه‌ای (استندآپ) برگزار می‌کند. اکنون نوبت صحبت شماست.",
            immigrantMission = "Provide a concise 3-part update: 1) What you accomplished yesterday, 2) What you will do today, 3) Any blockers requiring help.",
            listeningDialogue = listOf(
                DialogueLine(
                    speaker = "Scrum Master (David)",
                    speakerRole = "Meeting Host",
                    textEn = "Thanks Elena. Next up, Ali, how are things going on your end?",
                    textFa = "ممنون النا. نفر بعد، علی، اوضاع تسک‌های شما چطور پیش می‌رود؟"
                ),
                DialogueLine(
                    speaker = "Ali (You)",
                    speakerRole = "Engineer",
                    textEn = "Morning team. Yesterday, I finished setting up the local database and resolved the authentication bug. Today, I'll be working on the API integration for user profiles. No blockers on my side.",
                    textFa = "صبح بخیر تیم. دیروز راه‌اندازی دیتابیس را تمام کردم و باگ احراز هویت را برطرف کردم. امروز روی یکپارچه‌سازی API برای پروفایل کاربران کار خواهم کرد. هیچ مانعی در کارم ندارم."
                ),
                DialogueLine(
                    speaker = "Scrum Master (David)",
                    speakerRole = "Meeting Host",
                    textEn = "Great progress. Let's sync with Elena offline if you need test tokens for the user profile endpoint.",
                    textFa = "پیشرفت عالی بود. اگر برای اندپوینت پروفایل به توکن تست نیاز داشتی بعد از جلسه با النا هماهنگ شو."
                )
            ),
            comprehensionQuestions = listOf(
                ComprehensionQuestion(
                    id = 1,
                    question = "What are the standard three questions answered in a daily workplace standup?",
                    questionFa = "سه سوال استانداردی که در جلسات استندآپ کاری پاسخ داده می‌شوند کدامند؟",
                    options = listOf(
                        "Salary expectations, vacation requests, and lunch plans",
                        "What was done yesterday, what will be done today, and any blockers",
                        "Personal weekend plans and hobbies",
                        "Company quarterly profit review"
                    ),
                    correctIndex = 1,
                    explanation = "Standup follows the 3-question rule: Yesterday's progress, Today's plan, and Blockers."
                )
            ),
            vocabularyList = listOf(
                VocabWord(
                    word = "blocker",
                    pronunciation = "/ˈblɑː.kɚ/",
                    partOfSpeech = "noun",
                    meaningEn = "An obstacle or dependency that prevents you from completing your work.",
                    meaningFa = "مانع کاری / وابستگی به کار دیگران که مانع پیشرفت تسک شما شده است",
                    exampleEn = "I have one blocker: waiting for design team approval on the color schema.",
                    exampleFa = "یک مانع کاری دارم: منتظر تایید تیم طراحی روی پالت رنگی هستم.",
                    collocation = "no blockers, encounter a blocker"
                ),
                VocabWord(
                    word = "sync offline",
                    pronunciation = "/sɪŋk ˌɔːfˈlaɪn/",
                    partOfSpeech = "phrase",
                    meaningEn = "To discuss a specific detailed topic with someone outside the main group meeting.",
                    meaningFa = "گفتگوی دونفره بعد از جلسه اصلی برای صرفه‌جویی در وقت بقیه تیم",
                    exampleEn = "Let's sync offline on Slack after the standup finishes.",
                    exampleFa = "بیایید بعد از اتمام جلسه، در اسلک به صورت خصوصی موضوع را بررسی کنیم.",
                    collocation = "sync offline with a colleague"
                )
            ),
            grammarInContext = GrammarPoint(
                topic = "Past Simple vs Future Continuous in Standup Updates",
                topicFa = "گذشته ساده و آینده استمراری در گزارش‌های کاری",
                coreRule = "Use Past Simple for yesterday ('Yesterday I completed / resolved') and Future Continuous / 'will be -ing' for today ('Today I will be working on...').",
                whyItMattersForMigration = "Using crisp verb tenses shows senior technical maturity and clarity in sprint planning.",
                whyItMattersFa = "شفافیت در بیان زمان افعال، تسلط و اعتمادبه‌نفس کاری شما را در برابر مدیران خارجی اثبات می‌کند.",
                examples = listOf(
                    "Yesterday I pushed the new code." to "دیروز کد جدید را ثبت کردم.",
                    "Today I will be reviewing pull requests." to "امروز به بررسی پول‌ریکوئست‌ها خواهم پرداخت."
                ),
                commonPersianMistake = "Persian speakers say: 'Yesterday I am do fix bug, today I do code.'",
                correctForm = "Say: 'Yesterday I fixed the bug, and today I will be coding the new endpoint.'"
            ),
            speakingPractices = listOf(
                SpeakingTask(
                    promptEn = "Say aloud: Give your 30-second standup update to the team.",
                    promptFa = "با صدای بلند بگویید: گزارش کاری ۳۰ ثانیه‌ای خود را به تیم ارائه دهید.",
                    targetPhrase = "Yesterday I finalized the report, today I am drafting the proposal, and I have no blockers.",
                    targetPhraseFa = "دیروز گزارش را نهایی کردم، امروز پیش‌نویس پروپوزال را می‌نویسم، و هیچ مانعی ندارم.",
                    sampleAnswer = "Hi team, yesterday I tested the flow, today I will be implementing the review screen. No blockers.",
                    pronunciationTips = "Speak at a steady cadence and pause slightly between 'Yesterday', 'Today', and 'Blockers'."
                )
            ),
            readingDocument = ReadingMaterial(
                type = "Agile Team Sprint Board Guidelines",
                headline = "Engineering Standup Etiquette & Best Practices",
                headlineFa = "قوانین و آداب حرفه‌ای جلسات استندآپ چابک",
                content = """
STANDUP BEST PRACTICES:
1. Keep your update strictly under 90 seconds.
2. Focus on outcomes rather than every minute activity.
3. If a blocker requires more than 2 minutes of discussion, flag it as a 'parking lot item' to sync offline after the call.
4. Update your Jira/Trello tickets prior to 9:15 AM.
                """.trimIndent(),
                keyTakeawaysEn = listOf(
                    "Updates should be concise and focused on high-level deliverables.",
                    "Technical debates should happen offline so the whole team's time is preserved."
                ),
                keyTakeawaysFa = listOf(
                    "گزارش‌ها باید بسیار خلاصه (زیر ۹۰ ثانیه) و متمرکز بر خروجی باشند.",
                    "بحث‌های فنی طولانی باید به بعد از جلسه موکول شوند تا وقت سایر همکاران گرفته نشود."
                )
            ),
            writingTask = WritingTask(
                promptEn = "Write an async standup message in your team's Slack #daily-standup channel.",
                promptFa = "یک متن گزارش استندآپ متنی برای کانال اسلک تیم بنویسید.",
                contextDescription = "Async standup formats use bullet points: Yesterday, Today, Blockers.",
                starterText = "Standup Update - Ali:\n- Yesterday: ...\n- Today: ...\n- Blockers: ...",
                requiredKeywords = listOf("yesterday", "today", "blockers", "completed", "working on"),
                idealModelAnswer = "Standup Update - Ali:\n- Yesterday: Completed unit tests for payment service.\n- Today: Working on error handling and retry logic.\n- Blockers: None at this time."
            ),
            finalChallenge = SimulationChallenge(
                scenarioTitle = "Live Simulation: Agile Standup Call",
                partnerRole = "Team Lead (Sarah)",
                partnerOpeningLine = "Morning Ali, you're up next! How are your sprint tasks progressing?",
                missionObjectiveEn = "Give your update on yesterday, today, and confirm whether you have any blockers.",
                missionObjectiveFa = "گزارش دیروز و امروز را بدهید و وضعیت موانع کاری را اعلام کنید.",
                expectedKeywords = listOf("yesterday", "today", "blocker", "completed", "working")
            )
        )
    )

    fun getLessonById(id: String): FullLessonContent {
        return LESSONS_MAP[id] ?: LESSONS_MAP["phase2_lesson1_landlord"]!!
    }

    fun getAllFullLessons(): List<FullLessonContent> {
        return LESSONS_MAP.values.toList()
    }
}
