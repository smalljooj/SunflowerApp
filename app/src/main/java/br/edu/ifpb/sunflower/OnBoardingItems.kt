package br.edu.ifpb.sunflower

class OnBoardingItems(
    val image: Int,
    val title: Int,
) {
    companion object {
        fun getData(): List<OnBoardingItems> {
            return listOf(
                OnBoardingItems(R.drawable.welcome1, R.string.title1),
                OnBoardingItems(R.drawable.welcome2, R.string.title2),
                OnBoardingItems(R.drawable.welcome3, R.string.title3)
            )
        }
    }
}