using System;

namespace WSISmile.Business.Enum
{
    /// <summary>
    /// 外部出力処理状態Enum
    /// </summary>
    public enum OutputStatus
    {
        /// <summary>
        /// なし
        /// </summary>
        None = -1,
        /// <summary>
        /// 0：準備中
        /// </summary>
        Preparing = 0,
        /// <summary>
        /// 1：出力中
        /// </summary>
        Outputing = 1,
        /// <summary>
        /// 2：受入中
        /// </summary>
        Accepting = 2,
        /// <summary>
        /// 3：テスト完了
        /// </summary>
        TestFinished = 3,
        /// <summary>
        /// 4：中断終了
        /// </summary>
        Suspension = 4,
        /// <summary>
        /// 5：異常終了
        /// </summary>
        FinishedByError = 5,
        /// <summary>
        /// 6：チェック中
        /// </summary>
        Checking = 6,
        /// <summary>
        /// 7：出力完了
        /// </summary>
        OutputFinished = 7,
        /// <summary>
        /// 8：受入完了
        /// </summary>
        AcceptFinished = 8
    }
}
