# Report
## Overview
This project implements and evaluates four classic **divide-and-conquer algorithms** in Java:
- **MergeSort** – Θ(n log n), linear merge, reusable buffer, insertion sort cutoff  
- **QuickSort** – randomized pivot, recurse on smaller partition (stack O(log n))  
- **Deterministic Select** – Median-of-Medians (MoM5), Θ(n), recurse only where needed  
- **Closest Pair of Points (2D)** – geometric D&C, O(n log n), strip check with ≤7 neighbors  
All algorithms are instrumented with a **metrics tracker** that records:
- Comparisons  
- Allocations  
- Recursion depth  
- Running time  

Results are exported to `results.csv` for analysis and plotting.
## Architecture Notes
- **Metrics:** Each algorithm accepts a shared `Metrics` object that counts comparisons, allocations, and recursion depth.  
- **Recursion control:** Depth is tracked with enter/exit hooks. QuickSort always recurses on the smaller side to guarantee logarithmic depth.  
- **Memory:** MergeSort reuses a buffer, Select allocates only for median groups, Closest Pair allocates temporary strip arrays.  
- **Cutoffs:** MergeSort uses Insertion Sort for small arrays to reduce constant factors.
## Recurrence Analysis

### MergeSort
- Recurrence: `T(n) = 2T(n/2) + Θ(n)`  
- Master Theorem Case 2 ⇒ `T(n) = Θ(n log n)`  
- Depth = Θ(log n).  

### QuickSort (Randomized)
- Expected recurrence: `T(n) = T(αn) + T((1–α)n) + Θ(n)`  
- Expected depth = Θ(log n), worst-case Θ(n).  
- Comparisons ≈ `2n log n` on average.  

### Deterministic Select
- Recurrence: `T(n) = T(n/5) + T(7n/10) + Θ(n)`  
- Akra–Bazzi intuition ⇒ `T(n) = Θ(n)`  
- Depth = O(log n).  

### Closest Pair of Points
- Recurrence: `T(n) = 2T(n/2) + Θ(n)`  
- Master Case 2 ⇒ `T(n) = Θ(n log n)`  
- Strip check uses ≤7 neighbors per point ⇒ O(n).
##  Experimental Results
*(generated from `results.csv`)*

- **Time vs Input Size**:  
  - MergeSort and Closest Pair follow n log n.  
  - QuickSort is faster in practice despite same complexity.  
  - Deterministic Select shows linear scaling but higher constants.  

- **Depth vs Input Size**:  
  - MergeSort & Closest Pair: logarithmic depth.  
  - QuickSort: log depth on average, lower than MergeSort due to tail recursion.  
  - Select: very shallow recursion, proportional to log n.  
## Summary
The measurements confirm the theoretical growth rates: MergeSort and Closest Pair scale as Θ(n log n), QuickSort achieves near-logarithmic depth with randomized pivots, and Deterministic Select runs in linear time. In practice, QuickSort is often faster due to lower constants and cache-friendly behavior, while Deterministic Select suffers from overhead despite its optimal complexity. Overall, theory and practice align well, with constant factors explaining the small mismatches.
