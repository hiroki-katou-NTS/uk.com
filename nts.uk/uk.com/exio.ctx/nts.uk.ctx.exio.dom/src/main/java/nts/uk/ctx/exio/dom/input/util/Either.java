package nts.uk.ctx.exio.dom.input.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 正常系と異常系の2種類の戻り値を表現するクラス（Rightが正常系）
 *
 * @param <L> 異常系の型
 * @param <R> 正常系の型
 */
public interface Either<L, R> {
	
	/**
	 * 正常系のインスタンスを作る
	 * @param right
	 * @return
	 */
	public static <L, R> Either<L, R> right(R right) {
		return new Right<>(right);
	}
	
	/**
	 * 異常系のインスタンスを作る
	 * @param left
	 * @return
	 */
	public static <L, R> Either<L, R> left(L left) {
		return new Left<>(left);
	}
	
	public static <L> Either<L, Void> rightVoid() {
		return new Right<>(null);
	}
	
	public static <L> Either<L, Void> leftVoid(L left) {
		return new Left<>(left);
	}
	
	/**
	 * rightSupplierによって正常系のインスタンスを作るが、指定された例外がスローされた場合は異常系インスタンスを作る
	 * @param rightSupplier
	 * @param exceptionClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <L extends Exception, R> Either<L, R> tryCatch(Supplier<R> rightSupplier, Class<L> exceptionClass) {
		
		try {
			return right(rightSupplier.get());
		} catch (Exception ex) {
			if (ex.getClass().equals(exceptionClass)) {
				return Either.left((L) ex);
			}
			
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Rightであればtaskに値を渡して実行する
	 * @param task
	 * @return
	 */
	default Either<L, R> ifRight(Consumer<R> task) {
		return this;
	}
	
	/**
	 * Leftであればtaskに値を渡して実行する
	 * @param task
	 * @return
	 */
	default Either<L, R> ifLeft(Consumer<L> task) {
		return this;
	}
	
	/**
	 * Rightであればそれを、Leftであればotherの実行結果を返す
	 * @param other
	 * @return
	 */
	R getOrElseGet(Function<? super L,? extends R> other);
	
	/**
	 * Rightであればそれを返し、LeftであればexceptionFunctionが返した例外オブジェクトをスローする
	 * @param exceptionFunction
	 * @return
	 */
	<X extends RuntimeException> R getOrElseThrow(Function<? super L, X> exceptionFunction);

	/**
	 * LeftとRightを両方変換する
	 * @param leftMapper
	 * @param rightMapper
	 * @return
	 */
	<L2, R2> Either<L2, R2> map(
			Function<? super L, ? extends L2> leftMapper,
			Function<? super R, ? extends R2> rightMapper);
	
	/**
	 * Rightを変換する
	 * @param rightMapper
	 * @return
	 */
	default <R2> Either<L, R2> map(Function<? super R, ? extends R2> rightMapper) {
		return map(l -> l, rightMapper);
	}
	
	/**
	 * Rightを変換するが、その戻り値がLeftであればLeftに変換する
	 * @param rightEitherMapper
	 * @return
	 */
	<R2> Either<L, R2> mapEither(Function<? super R, Either<L, R2>> rightEitherMapper);
	
	/**
	 * Leftを変換する
	 * @param leftMapper
	 * @return
	 */
	default <L2> Either<L2, R> mapLeft(Function<? super L, ? extends L2> leftMapper) {
		return map(leftMapper, r -> r);
	}

	@RequiredArgsConstructor
	@ToString
	@EqualsAndHashCode
	public static class Right<L, R> implements Either<L, R> {

		private final R value;
		
		@Override
		public Either<L, R> ifRight(Consumer<R> task) {
			task.accept(value);
			return this;
		}

		@Override
		public R getOrElseGet(Function<? super L, ? extends R> other) {
			return value;
		}

		@Override
		public <X extends RuntimeException> R getOrElseThrow(Function<? super L, X> exceptionFunction) {
			return value;
		}

		@Override
		public <L2, R2> Either<L2, R2> map(
				Function<? super L, ? extends L2> leftMapper,
				Function<? super R, ? extends R2> rightMapper) {
			return Either.right(rightMapper.apply(value));
		}

		@Override
		public <R2> Either<L, R2> mapEither(Function<? super R, Either<L, R2>> rightEitherMapper) {
			return rightEitherMapper.apply(value);
		}
		
	}

	@RequiredArgsConstructor
	@ToString
	@EqualsAndHashCode
	public static class Left<L, R> implements Either<L, R> {

		private final L value;
		
		@Override
		public Either<L, R> ifLeft(Consumer<L> task) {
			task.accept(value);
			return this;
		}

		@Override
		public R getOrElseGet(Function<? super L, ? extends R> other) {
			return other.apply(value);
		}

		@Override
		public <X extends RuntimeException> R getOrElseThrow(Function<? super L, X> exceptionFunction) {
			throw exceptionFunction.apply(value);
		}

		@Override
		public <L2, R2> Either<L2, R2> map(
				Function<? super L, ? extends L2> leftMapper,
				Function<? super R, ? extends R2> rightMapper) {
			return Either.left(leftMapper.apply(value));
		}

		@Override
		public <R2> Either<L, R2> mapEither(Function<? super R, Either<L, R2>> rightEitherMapper) {
			return Either.left(value);
		}
		
	}
}
