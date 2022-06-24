package com.mutant.api.service;

import com.mutant.api.model.DiagonalValidationDirection;
import com.mutant.api.util.Counter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class TraverseService {

  private DnaValidatorService dnaValidatorService;
  private ExecutorService executorService;

  public TraverseService(DnaValidatorService dnaValidatorService, ExecutorService executorService) {
    this.dnaValidatorService = dnaValidatorService;
    this.executorService = executorService;
  }

  public int findMutant(String[] dna) {
    Counter mutantCount = new Counter(0);

    List<Future<?>> traverseTasks;

    traverseTasks = initTraverseMatrixTasks(dna, mutantCount);

    while (mutantCount.getValue() < 2) {
      if (traverseTasks.stream().allMatch(Future::isDone)) return mutantCount.getValue();
    }

    return mutantCount.getValue();
  }

  private List<Future<?>> initTraverseMatrixTasks(String[] dna, Counter mutantCount) {
    List<Future<?>> tasks = new ArrayList<>();
    int midPoint = (dna[0].length() / 2);
    int length = dna.length;

    tasks.add(executorService.submit(traverseVerticalFromRight(dna, midPoint, length, mutantCount)));
    tasks.add(executorService.submit(traverseVerticalFromLeft(dna, midPoint, length, mutantCount)));

    tasks.add(executorService.submit(traverseHorizontalFromUp(dna, midPoint, length, mutantCount)));
    tasks.add(executorService.submit(traverseHorizontalFromDown(dna, midPoint, length, mutantCount)));

    tasks.add(executorService.submit(traverseDiagonalFromRightUp(dna, length, mutantCount)));
    tasks.add(executorService.submit(traverseDiagonalFromLeftUp(dna, length, mutantCount)));
    tasks.add(executorService.submit(traverseDiagonalFromRightDown(dna, length, mutantCount)));
    tasks.add(executorService.submit(traverseDiagonalLeftDown(dna, length, mutantCount)));

    return tasks;
  }

  private Runnable traverseVerticalFromRight(String[] dna, int midPoint, int length, Counter mutantCounter) {
    return () -> {
      Counter verticalCounter = new Counter(1);
      for (int col = length - 1; col >= midPoint; col--) {
        for (int row = 1; row < length; row++) {
          if (mutantCounter.getValue() > 1) return;
          dnaValidatorService.validateVertical(dna, row, col, verticalCounter, mutantCounter);
        }
        verticalCounter.resetTo(1);
      }
    };
  }

  private Runnable traverseVerticalFromLeft(String[] dna, int midPoint, int length, Counter mutantCounter) {
    return () -> {
      Counter verticalCounter = new Counter(1);
      for (int col = 0; col < midPoint; col++) {
        for (int row = 1; row < length; row++) {
          if (mutantCounter.getValue() > 1) return;
          dnaValidatorService.validateVertical(dna, row, col, verticalCounter, mutantCounter);
        }
        verticalCounter.resetTo(1);
      }
    };
  }

  private Runnable traverseHorizontalFromUp(String[] dna, int midPoint, int length, Counter mutantCounter) {
    return () -> {
      Counter horizontalCounter = new Counter(1);
      for (int row = 0; row < midPoint; row++) {
        for (int col = 1; col < length; col++) {
          if (mutantCounter.getValue() > 1) return;
          dnaValidatorService.validateHorizontal(dna, row, col, horizontalCounter, mutantCounter);
        }
        horizontalCounter.resetTo(1);
      }
    };
  }

  private Runnable traverseHorizontalFromDown(String[] dna, int midPoint, int length, Counter mutantCounter) {
    return () -> {
      Counter horizontalCounter = new Counter(1);
      for (int row = length - 1; row >= midPoint; row--) {
        for (int col = 1; col < length; col++) {
          if (mutantCounter.getValue() > 1) return;
          dnaValidatorService.validateHorizontal(dna, row, col, horizontalCounter, mutantCounter);
        }
        horizontalCounter.resetTo(1);
      }
    };
  }

  private Runnable traverseDiagonalFromRightUp(String[] dna, int length, Counter mutantCount) {
    return () -> {
      Counter diagonalCounter = new Counter(1);
      for (int rowPivot = 3; rowPivot < length -1; rowPivot++) {
        for (int row = rowPivot, col = length - 1; row > 0; row--, col--) {
          if (mutantCount.getValue() > 1) return;
          dnaValidatorService.validateDiagonal(dna, row, col, DiagonalValidationDirection.LEFT_UP, diagonalCounter,
              mutantCount);
        }
        diagonalCounter.resetTo(1);
      }
    };
  }

  private Runnable traverseDiagonalFromLeftUp(String[] dna, int length, Counter mutantCount) {
    return () -> {
      Counter diagonalCounter = new Counter(1);
      for (int rowPivot = 3; rowPivot < length - 1; rowPivot++) {
        for (int row = rowPivot, col = 0; row > 0; row--, col++) {
          if (mutantCount.getValue() > 1) return;
          dnaValidatorService.validateDiagonal(dna, row, col, DiagonalValidationDirection.RIGHT_UP, diagonalCounter,
              mutantCount);
        }
        diagonalCounter.resetTo(1);
      }
    };
  }

  private Runnable traverseDiagonalLeftDown(String[] dna, int rowLength, Counter mutantCount) {
    return () -> {
      Counter diagonalCounter = new Counter(1);
      for (int rowPivot = rowLength - 4; rowPivot >= 0; rowPivot--) {
        for (int row = rowPivot, col = 0; rowLength - row > 1; row++, col++) {
          if (mutantCount.getValue() > 1) return;
          dnaValidatorService.validateDiagonal(dna, row, col, DiagonalValidationDirection.RIGHT_DOWN, diagonalCounter,
              mutantCount);
        }
        diagonalCounter.resetTo(1);
      }
    };
  }

  private Runnable traverseDiagonalFromRightDown(String[] dna, int length, Counter mutantCount) {
    return () -> {
      Counter diagonalCounter = new Counter(1);
      for (int rowPivot = length - 4; rowPivot >= 0; rowPivot--) {
        for (int row = rowPivot, col = length - 1; length - row > 1; row++, col--) {
          if (mutantCount.getValue() > 1) return;
          dnaValidatorService.validateDiagonal(dna, row, col, DiagonalValidationDirection.LEFT_DOWN, diagonalCounter,
              mutantCount);
        }
        diagonalCounter.resetTo(1);
      }
    };
  }
}