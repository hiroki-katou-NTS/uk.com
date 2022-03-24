using System;
using System.Collections.Generic;

using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Accept;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// 外部受入関連情報
    /// </summary>
    public class Accept
    {
        /// <summary>
        /// 外部受入条件コード (実行ing.)
        /// </summary>
        public string ConditionSetCd = "";

        /// <summary>
        /// 受入データの行数カウント(intMaxCntまでカウントしていく)
        /// </summary>
        public int RowCounter = 0;

        /// <summary>
        /// 外部受入前準備.TaskID
        /// </summary>
        public string PrepareTaskId = "";

        /// <summary>
        /// 外部受入実行.TaskID
        /// </summary>
        public string ExecuteTaskId = "";

        /// <summary>
        /// 外部受入アップロード.FileID
        /// </summary>
        public string FileId = "";

        /// <summary>
        /// 外部受入File(Path)
        /// </summary>
        public string File = "";

        /// <summary>
        /// 外部受入総件数
        /// </summary>
        public int TotalCount = 0;
    }
}
