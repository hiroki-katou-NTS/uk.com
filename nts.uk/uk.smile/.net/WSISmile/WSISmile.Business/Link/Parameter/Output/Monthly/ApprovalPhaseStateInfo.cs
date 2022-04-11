using System;
using Newtonsoft.Json.Linq;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// 承認フェーズ状態
    /// </summary>
    public class ApprovalPhaseStateInfo
    {
        #region コンストラクタ
        public ApprovalPhaseStateInfo(JObject aJObject)
        {
            if (aJObject["approvalAtr"] != null)
            {
                this._approvalAtr = Convert.ToBoolean(aJObject.GetValue("approvalAtr").ToObject<int>());
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private bool _approvalAtr = false;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 承認区分
        /// </summary>
        public bool ApprovalAtr
        {
            get { return _approvalAtr; }
            set { _approvalAtr = value; }
        }
        #endregion プロパティ
    }
}
