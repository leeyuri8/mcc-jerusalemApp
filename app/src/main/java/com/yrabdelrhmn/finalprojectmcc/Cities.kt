package com.yrabdelrhmn.finalprojectmcc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yrabdelrhmn.finalprojectmcc.model.DataCities
import com.yrabdelrhmn.finalprojectmcc.notification.MyApp
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Cities : Fragment() {

lateinit var cities:RecyclerView
    var adapterCities: GetCities? = null
    var db: FirebaseFirestore? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_cities, container, false)
        db = Firebase.firestore
        cities=root.findViewById(R.id.cities)

       /*AddCities(" تُعتبر مدينة غزّة أكبر مدينة في قطاع غزة وأرض فلسطين، ويبلغ عدد السكّان فيها ما يُقارب 410,000 نسمة في المدينة الداخليّة و1.4 مليون نسمة في المنطقة الحضريّة، وتُسمّى المدينة بمدينة غزة حتى يتمّ تمييزها وتفريقها عن قطاع غزة الكبير، كما تُعرف بأنّها أقدم مدينة في العالم، وتقع في الجنوب الغربي من فلسطين على الطريق الساحلي للبحر الأبيض المتوسط ما بين شمال قارة أفريقيا، والأراضي الخضراء في بلاد الشام \n"
        + "الجامعات والمعالم في مدينة غزة تحتوي مدينة غزّة على مجموعة من الجامعات، و المعالم، ومن أهمّها ما يأتي:[١] الجامعة الإسلاميّة. جامعة الأقصى. جامعة الأزهر. جامعة القدس المفتوحة. المسجد العمري الكبير. مسجد السيّد هاشم. مسجد ابن عثمان. مسجد ابن مروان. ضريح الشيخ أبو العزم. ضريح الشيخ عجلين. تلّ المنطار. قلعة نابليون (قلعة الرضوان). كنيسة سانت بورفيري.\n" +
                "\n" , 365.0,"https://www.youtube.com/watch?v=baGHaFYRDwI",
        arrayListOf(R.drawable.gaza1,R.drawable.gaza2,R.drawable.gaza3)
        ,"Gaza",31.50213130997636, 34.46675871156723)

        AddCities("تُعدّ مدينة القدس من المُدن الحضاريّة والمُقدسة المهمّة، أُسِّست معالمها الأولى على منطقة تلال الظهور التي تُطلّ على سلوان في الجهة الجنوبيّة الشرقيّة التابعة للمسجد الأقصى، أمّا امتدادها الجغرافيّ في الوقت الحالي فيبدأ من الجهة الجنوبيّة لجبال الخليل، والجهة الشماليّة لجبال نابلس، وتصل إلى الجهة الشرقيّة التابعة للبحر المتوسط، ويصل ارتفاعها فوق مستوى سطح البحر إلى ما يقارب 775م\n" +
                "\n",125.1,"https://www.youtube.com/watch?v=jJxHWB4au4k&t=347s", arrayListOf(R.drawable.aqsa1,R.drawable.aqsa2,R.drawable.aqsa3,R.drawable.aqsa4,R.drawable.agsa5,R.drawable.agsa6)
        ,"Jerusalem",31.77920347668554, 35.20348958883201)

        AddCities("تقع مدينة بيت لحم على مسافة 10 كم إلى الجنوب من البلدة القديمة في القدس، ويعود سبب تسميتها ببيت لحم إلى كثرة اللحوم فيها نظراً لكثرة المراعي الخصبة والمواشي، وتُعدّ مدينة بيت لحم مدينةً كنعانيةً قديمةً سكنها الكنعانيون منذ زمن بعيد، وأطلقوا عليها اسم بيت إيلي لاحما، حيث إيل تعني إله، ولاحما اسم يُطلق على إله الحصاد والخصوبة عند الكنعانيين، ثمّ توالت عليها مجموعات قبائل مختلفة، وتقع بيت لحم في موقع جبلي على ارتفاع 772 م فوق مستوى البحر الأبيض المتوسط، وعليه فهي تتمتّع بمناخ البحر الأبيض المتوسط حيث يكون الصيف حارّاً وجافاً، والشتاء بارداً وممطراً\n" +
                "\n",575.0,"https://www.youtube.com/watch?v=kgVKiC8tAbw", arrayListOf(R.drawable.bethlehem1,R.drawable.bethlehem2,R.drawable.bethlehem3),"Bethlehem",31.705783844699262, 35.20282550200884)

        AddCities("تعدّدت الآراء حول أصل تسمية مدينة رام الله بهذا الاسم، إلا أنّ أكثرها قرباً من الصحة هو أنّ كلمة رام هي كلمة ذات أصل كنعاني بمعنى المنطقة المرتفعة، وكلمة الله هي من إضافة العرب لتُصبح رام الله، وتبلغ مساحة مدينة رام الله حوالي 18.6 ألف كم2، وهي مدينة جبلية حيث بُنيت على جبل له إطلالة على الساحل الفلسطيني في الغرب، ويُحيطها من جهة الشرق والجنوب الجبال، وتبعد عن القدس عاصمة فلسطين مسافة 15 كم فقط، وترتفع عن سطح البحر حوالي 830 - 880 متر.[٤] يعود تأسيس مدينة رام الله إلى القرن السادس عشر الميلادي على يد أحد المشايخ الذي يُدعى راشد الحدادين، ومع مرور الزمن بدأت رام الله بالتوسّع؛ ففي العام 1807م بُنيت فيها أول كنيسة للروم الأرثوذكس، وفي عام 1869م افتُتحت فيها مدرسة الفرندز للبنات.\n" +
                "\n",16.3,"https://www.youtube.com/watch?v=cOagO1L6chg", arrayListOf(R.drawable.ramallah1,R.drawable.bethlehem2,R.drawable.ramallah3)
        ,"Ramallah",31.906866768765994, 35.204880200436634)
        AddCities("تُشير الأبحاث تاريخياً الى أنّ مدينة نابلس سُكنت لأول مرّة في الألفية الرابعة قبل الميلاد، إلّا أنّها لم تكتسب مكانتها البارزة إلّا عندما بنى الرومان مدينةً جديدةً فيها أطلقوا عليها اسم فلافيا نيابوليس، وفي أوائل القرن السابع ميلادي دخل المسلمون المدينة وأصبحت تحت حكمهم، واستمرّت المدينة بالازدهار اجتماعياً واقتصادياً، وبُني فيها العديد من المساجد، والمدارس، وغير ذلك، وفي أوائل القرن العشرين الميلادي تعرّضت المنطقة لزلزال قوي دُمِّرت جرائه العديد من مباني المدينة التاريخية.[٥] كانت نابلس خلال الانتداب البريطاني ساحةً للاشتباكات المُسلّحة، ممّا تسبّب في المزيد من دمارها، وبعد حرب عام 1948م استقبلت نابلس آلاف الفلسطينيين اللاجئين الذين نزحوا من المناطق التي استولت عليها إسرائيل، وتُعتبر مدينة نابلس اليوم مركزاً تجاريّاً حيويّاً، فهي موطن لأكبر جامعة في فلسطين وهي جامعة النجاح الوطنية، كما أنّها مليئة بالأسواق، والخانات المزدحمة، والمطاعم التقليدية، ومصانع الصابون، وغيرها من محلات الحِرف اليدوية، بالإضافة إلى المراكز الطبية والحمامات التقليدية القديمة.\n" +
                "\n",28.6,"https://www.youtube.com/watch?v=_iSxcoDnJRo", arrayListOf(R.drawable.nablus1,R.drawable.nablus2,R.drawable.nablus3)
        ,"Nablus",32.22462993385203, 35.26120353300989)

        AddCities("تُعدّ يافا إحدى نوافذ فلسطين على البحر المتوسط؛ حيث تقع على الشاطئ الشرقي للبحر المتوسط إلى الجنوب من مصب نهر العوجا بنحو 7 كم، وإلى الشمال الغربي من مدينة القدس بنحو 60 كم، وأكسبها موقعها الجغرافي أهميةً حربية، وتجارية، وزراعية، ممّا جعلها مطمعاً للعديد من الدول والأقوام على مرّ الزمان، كما يمتاز إقليم يافا بانبساط أرضه، وخصوبة تربته، ووفرة المياه فيه، واعتدال مناخه، وتتفاوت طبيعة ساحل يافا المستقيم ما بين جروف صخرية ينتصب بعضها فيعلو نحو 35 متر عن سطح البحر، والشواطئ الرملية الضحلة التي تتخللها بعض المستنقعات عند مصبّات بعض المجاري المائية.[٩] يسود يافا مناخ حوض البحر المتوسط المعتدل في يافا، حيث تهطل الأمطار فيها خلال النصف الشتوي من السنة، وتكون الرطوبة النسبية عاليةً صيفاً وشتاءً، وكانت يافا في بداية الألفية الرابعة قبل الميلاد مأهولةً بالسكان، ثمّ منذ منتصف الألفية الثالثة قبل الميلاد بدأت هجرة الموجات السامية من الجزيرة العربية نحو غربي آسيا، ويعود بناء مدينة يافا إلى الموجة الثالثة منها، وخضعت يافا للحكم الآشوري، والبابلي، والفارسي، واليوناني، والروماني، والبيزنطيّ، وصولاً إلى الفتح الإسلامي، فقد حكمها المماليك والعثمانيون، وفي النصف الثاني للقرن التاسع عشر ميلادي خضعت للانتداب البريطاني.\n" +
                "\n" ,6.4,"https://www.youtube.com/watch?v=AVRO9lYFwkU", arrayListOf(R.drawable.jaffa1,R.drawable.jaffa2,R.drawable.jaffa3)
        ,"Jaffa",32.05033611868842, 34.75794573933788)*/

        val query = db!!.collection("cities")
        val options =
                FirestoreRecyclerOptions.Builder<DataCities>().setQuery(query, DataCities::class.java).build()
        adapterCities = context?.let { GetCities(it,options) }

       GridLayoutManager(
                context, // context
                3, // span count
                RecyclerView.VERTICAL, // orientation
                false // reverse layout
        ).apply {
            // specify the layout manager for recycler view
            cities.layoutManager = this
        }

        // finally, data bind the recycler view with adapter
        cities.adapter =adapterCities

        return root
    }

    private fun AddCities(
        CityDetails: String,
        CityArea: Double,
        CityVideo: String,
        fileImageList: MutableList<Int>,
        CityName: String,
        latitude: Double,
        longitude: Double
    ) {
        val id = UUID.randomUUID().toString()
        val Products =
            hashMapOf(
                "id" to id,
                "CityVideo" to CityVideo,
                "CityDetails" to CityDetails,
                "CityArea" to CityArea,
                "CityPictures" to fileImageList,
                "CityName" to CityName,
                "latitude" to latitude,
                "longitude" to longitude
            )


        db!!.collection("cities").document(id).set(Products).addOnSuccessListener { void ->

            Log.e("T.H.A FireStore", "Added success $void")
            val intent = Intent(context, MainActivity::class.java)


            val manager = MyApp(context)
            manager.showSmallNotification(
                1,
                getString(R.string.app_name),
                "Product added successfully ",
                intent, R.drawable.check,
                R.color.white


            )


        }.addOnFailureListener { exception ->
            Log.e("T.H.A FireStore", "Error : ${exception.message}")
            val intent = Intent(context, MainActivity::class.java)


            val manager = MyApp(context)
            manager.showSmallNotification(
                1,
                getString(R.string.app_name),
                "Failed to add product ${exception.message}",
                intent, R.drawable.error,
                R.color.white


            )
        }

    }

    override fun onStart() {
        super.onStart()
        adapterCities!!.startListening()

    }

    override fun onStop() {
        super.onStop()
        adapterCities!!.stopListening()

    }

}