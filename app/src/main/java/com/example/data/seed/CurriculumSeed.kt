package com.example.data.seed

import com.example.data.local.LessonEntity
import com.example.data.local.VocabEntity
import com.example.data.local.WeakPointEntity
import com.example.data.model.*

object CurriculumSeed {

    val INITIAL_LESSONS = listOf(
        LessonEntity(
            id = "phase1_lesson1_intro",
            phaseNumber = 1,
            categoryIcon = "👋",
            titleEn = "Introducing Yourself & Personal Info",
            titleFa = "معرفی خود و ارائه اطلاعات فردی",
            estimatedMinutes = 20,
            isCompleted = true,
            isLocked = false,
            lastScore = 92,
            completedAtMillis = System.currentTimeMillis() - 86400000L * 2
        ),
        LessonEntity(
            id = "phase1_lesson2_shopping",
            phaseNumber = 1,
            categoryIcon = "🛒",
            titleEn = "Shopping & Daily Needs",
            titleFa = "خرید مایحتاج و سوال درباره قیمت‌ها",
            estimatedMinutes = 20,
            isCompleted = true,
            isLocked = false,
            lastScore = 88,
            completedAtMillis = System.currentTimeMillis() - 86400000L
        ),
        LessonEntity(
            id = "phase1_lesson3_directions",
            phaseNumber = 1,
            categoryIcon = "🧭",
            titleEn = "Getting Around & Asking Directions",
            titleFa = "مسیریابی و استفاده از حمل‌ونقل عمومی",
            estimatedMinutes = 22,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase2_lesson1_landlord",
            phaseNumber = 2,
            categoryIcon = "🏠",
            titleEn = "Talking to Landlord: Reporting a Leak",
            titleFa = "گفتگو با صاحب‌خانه: گزارش نشتی و خرابی",
            estimatedMinutes = 25,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase2_lesson2_bank",
            phaseNumber = 2,
            categoryIcon = "🏦",
            titleEn = "Opening a Bank Account & Debit Card",
            titleFa = "افتتاح حساب بانکی و کارت اعتباری",
            estimatedMinutes = 25,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase2_lesson3_doctor",
            phaseNumber = 2,
            categoryIcon = "🏥",
            titleEn = "Clinic Visit: Describing Symptoms",
            titleFa = "ویزیت پزشک: شرح دقیق علائم بیماری",
            estimatedMinutes = 25,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase2_lesson4_pharmacy",
            phaseNumber = 2,
            categoryIcon = "💊",
            titleEn = "Pharmacy & Prescription Instructions",
            titleFa = "داروخانه و درک دستور مصرف داروها",
            estimatedMinutes = 20,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase3_lesson1_firstday",
            phaseNumber = 3,
            categoryIcon = "💼",
            titleEn = "First Day at Work & Team Intro",
            titleFa = "روز اول کاری و معرفی حرفه‌ای به تیم",
            estimatedMinutes = 25,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase3_lesson2_standup",
            phaseNumber = 3,
            categoryIcon = "📊",
            titleEn = "Daily Standup: Giving Task Updates",
            titleFa = "جلسه کوتاه کاری و ارائه وضعیت تسک‌ها",
            estimatedMinutes = 22,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase3_lesson3_polite_email",
            phaseNumber = 3,
            categoryIcon = "✉️",
            titleEn = "Writing Polite Workplace Request Emails",
            titleFa = "نگارش ایمیل‌های رسمی و محترمانه اداری",
            estimatedMinutes = 25,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase3_lesson4_interview",
            phaseNumber = 3,
            categoryIcon = "🎯",
            titleEn = "Job Interview: Explaining Past Experience",
            titleFa = "مصاحبه شغلی و بیان سوابق کاری به انگلیسی",
            estimatedMinutes = 30,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase4_lesson1_prof_office",
            phaseNumber = 4,
            categoryIcon = "🎓",
            titleEn = "Visiting a Professor During Office Hours",
            titleFa = "مراجعه به دفتر استاد و رفع اشکال درسی",
            estimatedMinutes = 25,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase4_lesson2_lecture",
            phaseNumber = 4,
            categoryIcon = "📖",
            titleEn = "Understanding University Lectures",
            titleFa = "درک کلاس‌های درس دانشگاهی و نکته‌برداری",
            estimatedMinutes = 25,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        ),
        LessonEntity(
            id = "phase4_lesson3_research",
            phaseNumber = 4,
            categoryIcon = "🔬",
            titleEn = "Defending Opinions in Academic Seminars",
            titleFa = "بیان و دفاع از دیدگاه در سمینارهای دانشگاهی",
            estimatedMinutes = 28,
            isCompleted = false,
            isLocked = false,
            lastScore = 0
        )
    )

    val INITIAL_VOCAB = listOf(
        VocabEntity(
            word = "landlord",
            pronunciation = "/ˈlænd.lɔːrd/",
            partOfSpeech = "noun",
            meaningEn = "A person who rents land, a building, or an apartment to a tenant.",
            meaningFa = "صاحب‌خانه / موجر",
            exampleEn = "I texted my landlord about the leaking pipe in the bathroom.",
            exampleFa = "به صاحب‌خانه‌ام در مورد لوله آبی که در حمام نشتی دارد پیام دادم.",
            collocation = "contact your landlord",
            relatedLessonId = "phase2_lesson1_landlord",
            masteryStatus = "PRACTICING",
            reviewCount = 2
        ),
        VocabEntity(
            word = "tenant",
            pronunciation = "/ˈten.ənt/",
            partOfSpeech = "noun",
            meaningEn = "A person who pays rent for the use of land or a building.",
            meaningFa = "مستاجر",
            exampleEn = "The tenant is responsible for keeping the apartment clean.",
            exampleFa = "مستاجر مسئول تمیز نگه داشتن آپارتمان است.",
            collocation = "new tenant, rights of tenants",
            relatedLessonId = "phase2_lesson1_landlord",
            masteryStatus = "INTRODUCED"
        ),
        VocabEntity(
            word = "leak",
            pronunciation = "/liːk/",
            partOfSpeech = "verb / noun",
            meaningEn = "To let water or liquid escape through a hole or crack.",
            meaningFa = "نشتی داشتن / چکه کردن / چکه آب",
            exampleEn = "The kitchen faucet has been leaking since yesterday evening.",
            exampleFa = "شیر آب آشپزخانه از دیروز غروب در حال چکه کردن است.",
            collocation = "water leak, roof leak",
            relatedLessonId = "phase2_lesson1_landlord",
            masteryStatus = "STRONG",
            reviewCount = 4
        ),
        VocabEntity(
            word = "urgent",
            pronunciation = "/ˈɜːr.dʒənt/",
            partOfSpeech = "adjective",
            meaningEn = "Requiring immediate action or attention.",
            meaningFa = "فوری / اضطراری",
            exampleEn = "This is an urgent maintenance request because water is dripping on the floor.",
            exampleFa = "این یک درخواست تعمیرات فوری است چون آب روی کف زمین می‌ریزد.",
            collocation = "urgent request, urgent matter",
            relatedLessonId = "phase2_lesson1_landlord",
            masteryStatus = "PRACTICING",
            reviewCount = 1
        ),
        VocabEntity(
            word = "maintenance",
            pronunciation = "/ˈmeɪn.tən.əns/",
            partOfSpeech = "noun",
            meaningEn = "The work needed to keep something in good condition.",
            meaningFa = "تعمیرات و نگهداری",
            exampleEn = "The building maintenance team will arrive at 10 AM tomorrow.",
            exampleFa = "تیم تعمیرات و نگهداری ساختمان فردا ساعت ۱۰ صبح می‌رسند.",
            collocation = "maintenance request, building maintenance",
            relatedLessonId = "phase2_lesson1_landlord",
            masteryStatus = "NEEDS_REVIEW",
            reviewCount = 2
        ),
        VocabEntity(
            word = "colleague",
            pronunciation = "/ˈkɑː.liːɡ/",
            partOfSpeech = "noun",
            meaningEn = "A person with whom one works in a profession or business.",
            meaningFa = "همکار",
            exampleEn = "Let me introduce you to my colleague Sarah from the marketing team.",
            exampleFa = "اجازه دهید شما را به همکارم سارا از تیم بازاریابی معرفی کنم.",
            collocation = "work colleague, close colleague",
            relatedLessonId = "phase3_lesson1_firstday",
            masteryStatus = "STRONG",
            reviewCount = 3
        ),
        VocabEntity(
            word = "clarify",
            pronunciation = "/ˈkler.ə.faɪ/",
            partOfSpeech = "verb",
            meaningEn = "To make a statement or situation less confused and more clearly comprehensible.",
            meaningFa = "شفاف‌سازی کردن / توضیح بیشتر دادن",
            exampleEn = "Could you please clarify the deadline for this project?",
            exampleFa = "آیا ممکن است لطفاً مهلت تحویل این پروژه را شفاف‌سازی کنید؟",
            collocation = "clarify a point, clarify expectations",
            relatedLessonId = "phase3_lesson2_standup",
            masteryStatus = "PRACTICING",
            reviewCount = 1
        ),
        VocabEntity(
            word = "prescription",
            pronunciation = "/prɪˈskrɪp.ʃən/",
            partOfSpeech = "noun",
            meaningEn = "An instruction written by a medical practitioner that authorizes a patient to be provided a medicine.",
            meaningFa = "نسخه پزشک",
            exampleEn = "You need a doctor's prescription to get this antibiotic at the pharmacy.",
            exampleFa = "برای دریافت این آنتی‌بیوتیک از داروخانه به نسخه پزشک نیاز دارید.",
            collocation = "fill a prescription, prescription medication",
            relatedLessonId = "phase2_lesson4_pharmacy",
            masteryStatus = "INTRODUCED"
        )
    )

    val INITIAL_WEAK_POINTS = listOf(
        WeakPointEntity(
            conceptName = "Past Simple vs Present Perfect for Duration",
            category = "Grammar",
            errorSummaryEn = "Saying 'I have headache from 2 days' instead of 'I have had a headache for two days'",
            errorSummaryFa = "استفاده نادرست از from به جای for برای بیان مدت زمان بیماری یا سکونت",
            explanationFa = "در فارسی می‌گوییم «از دو روز پیش سردرد دارم»، اما در انگلیسی برای بیان استمرار از گذشته تا اکنون از Present Perfect به همراه for استفاده می‌شود: I've had a headache for two days.",
            mistakeCount = 4,
            status = "WEAK",
            quickFixLessonId = "phase2_lesson3_doctor"
        ),
        WeakPointEntity(
            conceptName = "Articles with Singular Countable Nouns",
            category = "Grammar",
            errorSummaryEn = "Omitting 'a / an / the' before singular nouns (e.g., 'I sent email to landlord')",
            errorSummaryFa = "فراموش کردن حروف تعریف a/an/the قبل از اسم‌های مفرد قابل شمارش",
            explanationFa = "در زبان فارسی حرف تعریف نامعین اجباری نیست، اما در انگلیسی نمی‌توان اسم مفرد را بدون a/an یا the به کار برد: I sent an email to the landlord.",
            mistakeCount = 3,
            status = "NEEDS_REVIEW",
            quickFixLessonId = "phase2_lesson1_landlord"
        ),
        WeakPointEntity(
            conceptName = "Polite Work Requests with Modal Verbs",
            category = "Speaking",
            errorSummaryEn = "Using direct imperatives ('Give me the report') instead of polite modal phrases ('Could you please share...')",
            errorSummaryFa = "استفاده از جملات امری مستقیم به جای ساختارهای محترمانه انگلیسی کار",
            explanationFa = "در محیط‌های کاری انگلیسی‌زبان، لحن مستقیم غیرمحترمانه تلقی می‌شود. همیشه از Could you please یا Would you mind استفاده کنید.",
            mistakeCount = 2,
            status = "NEEDS_REVIEW",
            quickFixLessonId = "phase3_lesson3_polite_email"
        ),
        WeakPointEntity(
            conceptName = "Pronoun Gender Agreement (He / She)",
            category = "Speaking",
            errorSummaryEn = "Mixing up 'He' and 'She' when referring to managers or landlords",
            errorSummaryFa = "جابجا گفتن He و She به دلیل عدم تمایز جنسیتی ضمیر «او» در فارسی",
            explanationFa = "چون در فارسی ضمیر «او» برای زن و مرد یکسان است، فارسی‌زبانان به صورت ناخودآگاه He و She را اشتباه به کار می‌برند. به ضمیر افراد در حین صحبت دقت کنید.",
            mistakeCount = 3,
            status = "WEAK",
            quickFixLessonId = "phase3_lesson1_firstday"
        )
    )
}
