# PriorityQueue

Not much to this. A persistent implementation of a priority queue using a persistent vector

## Usage

I imagine that you would use this within a clojure atom

Load the names at PriorityQueue.lib

e.g. (def atm (atom (EMPTY_HEAP >))
Empty heap takes a predicate as it's second argument. The predicate is stored in the heaps
metadata. If it is true for the form (fn x y)  x has greater priority than y.

Once within an atom, you can assign threads to change the data.
The operations are pure functions, and a priority queue is usually for work distribution among
multiple threads, so I'm assuming for this example that you don't require synchronization.

(swap! atm insert element)	  ; insert an element.
(top @atm) 	  		  ; return the max priority element
(swap! atm popheap)		  ; assign the atom to a heap with the maximum priority element removed.

Happy Heaping!

Keith Wyss

## License

Copyright (C) 2011 Keith Wyss

Distributed under the Eclipse Public License, the same as Clojure.
