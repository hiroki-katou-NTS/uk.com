using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Closure
{
    /// <summary>
    /// 雇用／締め情報
    /// </summary>
    public class EmploymentClosureInfo
    {
        #region コンストラクタ
        public EmploymentClosureInfo() { }

        /// <summary>
        /// GetEmploymentClosureInfoのレスポンス(JObject)からキャストする
        /// </summary>
        /// <param name="aJObject"></param>
        public EmploymentClosureInfo(JObject aJObject)
        {
            if (aJObject["employmentCd"] != null)
            {
                this._employmentCd = aJObject.GetValue("employmentCd").ToObject<string>();
            }
            if (aJObject["closureId"] != null)
            {
                this._closureId = aJObject.GetValue("closureId").ToObject<int>();
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private string _employmentCd = "";

        private int _closureId = 0;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 雇用コード
        /// </summary>
        public string EmploymentCd
        {
            get { return _employmentCd; }
            set { _employmentCd = value; }
        }

        /// <summary>
        /// 締めID
        /// </summary>
        public int ClosureId
        {
            get { return _closureId; }
            set { _closureId = value; }
        }
        #endregion プロパティ
    }
}
