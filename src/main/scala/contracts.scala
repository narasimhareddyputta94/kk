  // GENERATED
/**
  * Contract-based programming support.
  */
object contracts:
  /**
    * Extension for contracts on unimplemented methods.
    */
  extension (n: Nothing)
    def ensuring[T](p: T => Boolean, msg: => String = "") : Nothing =
      if p(n) then n
      else throw new java.lang.AssertionError("assertion failed: " + msg)
    end ensuring

end contracts
