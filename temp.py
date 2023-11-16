def max_subarray(numbers):
    """Find the largest sum of any contiguous subarray."""
    best_sum = 0  # or: float('-inf') for negative sums
    current_sum = 0
    for x in numbers:
        current_sum = max(x, current_sum + x)
        best_sum = max(best_sum, current_sum)
    return best_sum

arr = [1, 2, 3, 4, 5, 6, -7, 8, -9, -10]

print(max_subarray(arr))