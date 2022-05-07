object MyBlog {
  def main(args: Array[String]): Unit = {
    condExample()
  }

  //https://imprashantsable.blogspot.com/2022/05/scala-collection-aggregateexample.html
  def aggregateExample(input: List[Int]): Unit = {
    //Aggregate should be used with par to execute input parallel. input.par.aggregate
    val output = input.aggregate((0,0))(
      (acc, element) => (acc._1 + element, acc._2 + 1), // (1,1) (2,1) (3,1)(4,1)
      (accu, element) => (accu._1 + element._1, accu._2 + element._2) // (1+2+3+4) (1+1+1+1) (10,4)
    )
    println("Output: " + output) // (10,4)

    //Use foldLeft if we are not using par.
    val outputWithFoldLeft =  input.foldLeft((0,0))((acc,ele) => (acc._1 + ele, acc._2 + 1) )
    println("Output with FoldLeft: " + outputWithFoldLeft) //(10,4)
  }

  //https://imprashantsable.blogspot.com/2022/05/scala-collection-andthen.html
  def andThenExample() : Unit = {
    val add10 = (x:Int) => x + 10
    val add20 = (x:Int) => x + 20
    val addthen = add10 andThen(add20)
    val res = addthen(10)
    // 10 is passed to add10 and then it's output 20 pass to
    // next function add20.
    println(res) // 10 + 10 = add20(20) // 40
  }

  //https://imprashantsable.blogspot.com/2022/05/scala-collection-chain.html
  def chainExample() : Unit = {
    def appendCharAB = (input: String) => input + "AB"
    def appendCharXY = (input: String) => input + "XY"
    val res = Function.chain(List(appendCharAB, appendCharXY))("Prashant")
    println(s"Res = ${res}" ) //PrashantABXY

    def appendCharAB1 = (input: Int) => input + "AB1"
    def appendCharXY1 = (input: String) => input + "XY1"
    /*
      Below is not allowed as function appendCharAB1 should accept string not int
      val res1 = Function.chain(List(appendCharAB1, appendCharXY1))("Prashant")
      This is okay in andThen
    */
  }

  //https://imprashantsable.blogspot.com/2022/05/scala-collection-collect.html
  def collectExample(): Unit = {
    val divide: PartialFunction[Int, Int] = {
      case input: Int if(input != 0) => 100/input
    }
    val inputList = List(0, 0, 5, 15, 20)
    val res = inputList.collect(divide) // List(20, 6, 5) 100/5, 100/15, 100/20
    println(res)
  }

  //https://imprashantsable.blogspot.com/2022/05/scala-collection-combinations.html
  def combinationExample(): Unit = {
    val input = List(1,2,3)
    val combinations = input.combinations(2)
    println(combinations.toList) //List(List(1, 2), List(1, 3), List(2, 3))
  }

  //https://imprashantsable.blogspot.com/2022/05/scala-function-compose.html
  def composeExample(): Unit = {
    def appendCharAB = (input: Int) => { println("Executing function appendCharAB"); input + "AB" }
    def appendCharXY = (input: String) => { println("Executing function appendCharAB"); input + "XY" }

    val composeAnonymousFunction: Int => String = appendCharXY compose(appendCharAB)
    val res = composeAnonymousFunction(999)
    println(res) //999ABXY
  }
  //https://imprashantsable.blogspot.com/2022/05/scala-function-concat.html
  def concatExample(): Unit = {
    val input1 = List(1,2,3,4,5)
    val input2 = List(11,12,13,14,15)
    val res = input1 concat(input2)
    println(res) //List(1, 2, 3, 4, 5, 11, 12, 13, 14, 15)
  }

  //https://imprashantsable.blogspot.com/2022/05/scala-function-cond.html
  def condExample(): Unit = {
    val isAbove18 = (input: Int) => input > 18
    val res: Either[String, String] = Either.cond(isAbove18(20), "True", "False")
    println(s"Cond with Either: ${res}") // Right(True)

    val canDivide: PartialFunction[Int, Boolean] = {
      case input: Int if(input != 0 )  => true
    }

    val res2: Boolean = PartialFunction.cond(0)(canDivide)
    println(s"Partial Function with Cond: ${res2}")

    val res3: Option[Boolean] = PartialFunction.condOpt(1)(canDivide)
    println(s"Partial Function with condOpt: ${res3}")

    val res4 =  canDivide(0)
    println(s"${res4}") // MatchError: 0 (of class java.lang.Integer)

  }
}