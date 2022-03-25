using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// 外部出力処理状態＆実行結果(完了時に)
    /// </summary>
    public class OutputStatusInfo
    {
        #region コンストラクタ
        public OutputStatusInfo() { }

        /// <summary>
        /// OutputStatusCheckのレスポンス(JObject)からキャストする
        /// </summary>
        /// <param name="aJObject"></param>
        public OutputStatusInfo(JObject aJObject)
        {
            if (aJObject["exOutProId"] != null)
            {
                this._exOutProId = aJObject.GetValue("exOutProId").ToObject<string>();
            }
            if (aJObject["proCnt"] != null)
            {
                this._proCnt = aJObject.GetValue("proCnt").ToObject<int>();
            }
            if (aJObject["errCnt"] != null)
            {
                this._errCnt = aJObject.GetValue("errCnt").ToObject<int>();
            }
            if (aJObject["totalProCnt"] != null)
            {
                this._totalProCnt = aJObject.GetValue("totalProCnt").ToObject<int>();
            }
            if (aJObject["doNotInterrupt"] != null)
            {
                this._doNotInterrupt = Convert.ToBoolean(aJObject.GetValue("doNotInterrupt").ToObject<int>());
            }
            if (aJObject["proUnit"] != null)
            {
                this._proUnit = aJObject.GetValue("proUnit").ToObject<string>();
            }
            if (aJObject["opCond"] != null)
            {
                this._opCond = (OutputStatus)System.Enum.ToObject(typeof(OutputStatus), aJObject.GetValue("opCond").ToObject<int>());
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private string _exOutProId = "";

        private int _proCnt = 0;

        private int _errCnt = 0;

        private int _totalProCnt = 0;

        private bool _doNotInterrupt = false;

        private string _proUnit = "";

        private OutputStatus _opCond = OutputStatus.None;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// TaskID
        /// </summary>
        public string ExOutProId
        {
            get { return _exOutProId; }
            set { _exOutProId = value; }
        }

        /// <summary>
        /// 処理カウント
        /// </summary>
        public int ProCnt
        {
            get { return _proCnt; }
            set { _proCnt = value; }
        }

        /// <summary>
        /// エラー件数
        /// </summary>
        public int ErrCnt
        {
            get { return _errCnt; }
            set { _errCnt = value; }
        }

        /// <summary>
        /// 処理総数
        /// </summary>
        public int TotalProCnt
        {
            get { return _totalProCnt; }
            set { _totalProCnt = value; }
        }

        /// <summary>
        /// 処理中断フラグ
        /// </summary>
        public bool DoNotInterrupt
        {
            get { return _doNotInterrupt; }
            set { _doNotInterrupt = value; }
        }

        /// <summary>
        /// 処理単位
        /// </summary>
        public string ProUnit
        {
            get { return _proUnit; }
            set { _proUnit = value; }
        }

        /// <summary>
        /// 外部出力処理状態
        /// </summary>
        public OutputStatus OpCond
        {
            get { return _opCond; }
            set { _opCond = value; }
        }
        #endregion プロパティ
    }
}
