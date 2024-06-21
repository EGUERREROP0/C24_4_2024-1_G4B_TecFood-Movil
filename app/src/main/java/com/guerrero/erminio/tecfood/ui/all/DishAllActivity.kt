package com.guerrero.erminio.tecfood.ui.all

/*class DishAllActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDishallBinding

    private lateinit var retrofit: Retrofit
    private lateinit var adapter: DishAllAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrifit()
        initUI()
    }

    private fun initUI() {
        getAllDishes()

        //Crear adapter
        adapter = DishAllAdapter()
        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter
    }

    private fun getAllDishes() {
        binding.progressBarScreenMain.isVisible = true

        CoroutineScope(Dispatchers.IO).launch {
            val request = retrofit.create(ApiService::class.java).getPokemon(20)
            if (request.isSuccessful) {
                request.body()?.let {
                    runOnUiThread {
                        adapter.updateAllList(it.categories)
                        binding.progressBarScreenMain.isVisible = false
                    }
                }
                //Log.i("yo", "Funciona")
            }else{
                runOnUiThread{
                    binding.progressBarScreenMain.isVisible = false
                    binding.rvList.isVisible = false

                    binding.errorImage.isVisible = true

                }
                Log.i("yo", "No Funciona")
            }
        }

    }

    private fun getRetrifit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}*/