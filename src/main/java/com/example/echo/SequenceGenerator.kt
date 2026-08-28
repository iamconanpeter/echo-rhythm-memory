package com.example.echo
object SequenceGenerator { fun seedForDay(day: Int) = (day * 48271) % 4 }
class EchoSequence(val rings: List<Int>) { fun echo() = EchoSequence(rings.reversed()) }
