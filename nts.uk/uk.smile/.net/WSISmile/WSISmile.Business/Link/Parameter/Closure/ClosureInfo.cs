using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Closure
{
    /// <summary>
    /// 締め情報
    /// </summary>
    public class ClosureInfo
    {
        #region コンストラクタ
        public ClosureInfo() { }

        /// <summary>
        /// GetClosureInfoのレスポンス(JObject)からキャストする
        /// </summary>
        /// <param name="aJObject"></param>
        public ClosureInfo(JObject aJObject)
        {
            if (aJObject["closureId"] != null)
            {
                this._closureId = aJObject.GetValue("closureId").ToObject<int>();
            }
            if (aJObject["useAtr"] != null)
            {
                this._useAtr = Convert.ToBoolean(aJObject.GetValue("useAtr").ToObject<int>());
            }
            if (aJObject["closuresMonth"] != null)
            {
                this._closuresMonth = aJObject.GetValue("closuresMonth").ToObject<int>();
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private int _closureId = 0;

        private bool _useAtr = false;

        private int _closuresMonth = 0;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 締めID
        /// </summary>
        public int ClosureId
        {
            get { return _closureId; }
            set { _closureId = value; }
        }

        /// <summary>
        /// 利用区分
        /// </summary>
        public bool UseAtr
        {
            get { return _useAtr; }
            set { _useAtr = value; }
        }

        /// <summary>
        /// 当月
        /// </summary>
        public int CurrentMonth
        {
            get { return _closuresMonth; }
            set { _closuresMonth = value; }
        }
        #endregion プロパティ
    }
}
