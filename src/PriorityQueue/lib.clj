(ns PriorityQueue.lib)

(defn EMPTY_HEAP [priority]
  "Creates a new heap with a nil root node. Children of N are 2N + 1 and 2N + 2"
  (with-meta [] {:>priority priority})
  )

(defn top [heap]
  "Returns the root of a heap"
  (if (empty? heap)
    nil
    (heap 0))
  )

(defn- addbottom [heap x]
  "Adds x to bottom of heap"
  (conj heap x)
  )
(defn- parent [heap index]
  "Returns the parent of the heap"
  (cond (= 0 index) -1
        (even? index) (heap (/ (- index 2) 2))
        (odd? index) (heap (/ (- index 1) 2))
        )
  )

(defn- parindex [index]
  "Returns the parent of the heap"
  (cond (= 0 index) -1
        (not (integer? index)) (throw (java.lang.Exception. "That is not a valid integer"))
        (even? index) (int (/ (- index 2) 2))
        (odd? index) (int (/ (- index 1) 2))
        :else -1
        )
  )



(defn- swapnodes [heap index indextwo]
  "Swaps two nodes"

  (let [temp (heap index)]
    (assoc (assoc heap index (heap indextwo)) indextwo temp)
    )
  )

(defn- upheap [heap index]
  "Moves a function up the heap and returns result (:>priority must be defined)"
  (if (= index 0) heap
      (let [parent-index (parindex index) par (heap parent-index) child (heap index)]
        (cond 
         (((meta heap) :>priority) child par) (recur (assoc (assoc heap parent-index child) index par) (parindex index)) 
                                        ; swap two nodes
         :else heap
         )
        
        )
      )
  )
(def keepthis (atom (EMPTY_HEAP >)))

(defn insert [heap element]
  "Inserts an element will maintaining heap property (:>priority must be defined)"
  (upheap (addbottom heap element) (count heap))
  )

(defn- maxpriority [heap oneindex twoindex]
  "Determines the maximum priority of two indices"
  (if (((meta heap) :>priority) (heap oneindex) (heap twoindex))
    oneindex
    twoindex
    )
  )

(defn- downheap [heap index]
  "Sorts out an inconsistently low priority heap item"
  (let [chd1 (+ (* index 2) 1) chd2 (+ (* index 2) 2) lst (dec (count heap))] 
    (cond (and (> chd1 lst) (> chd2 lst)) heap ; if no children, return the heap
          (> chd2 lst)
          (if (((meta heap) :>priority) (heap chd1) (heap index)) 
            (recur (swapnodes heap index chd1) chd1)	; swap down to last node	
            heap		;else return heap
            )
          :else 
          (let [maxnd (maxpriority heap chd1 chd2)]
            (if (((meta heap) :>priority) (heap maxnd) (heap index)) 
              (recur (swapnodes heap index maxnd) maxnd)	; swap down to last node	
              heap		;else return heap
              )
            )
          )
    )
  )
(defn popheap [heap]
  "Pops best item off of heap"
  (let [lst (dec (count heap))]
    (if (empty? heap)
      heap
      (downheap (pop (swapnodes heap 0 lst)) 0)
      )
    )
  )

