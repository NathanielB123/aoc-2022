import strutils
import sequtils
from math import floorMod

type pair[a, b] = tuple[fst: a, snd: b]

let f = open("day2input.txt")
let s = f.readAll()
close(f)

let parsed = splitLines(s).map(proc(x: string): pair[int, int] = 
    let p = x.split(" ")
    (int(p[0][0]) - int('A'), int(p[1][0]) - int('X')))

proc toScore(rps: int, res: int): int = rps + 1 + res * 3

proc main() =
    let day1 = parsed.map(proc(p: pair[int, int]): int = 
        let (rpsA, rpsB) = p 
        toScore(rpsA, (rpsB - rpsA + 1).floorMod(3))).foldl(a + b)

    echo("Day 1: %i\n",day1)

    let day2 = parsed.map(proc(p: pair[int, int]): int = 
        let (rps, res) = p
        toScore((rps + res - 1).floorMod(3), res)).foldl(a + b)

    echo("Day 2: %i\n",day2)

main()