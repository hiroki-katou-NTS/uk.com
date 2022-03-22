using System;
using Newtonsoft.Json.Linq;

namespace WSISmile.Business.Link.Parameter.Accept
{
    /// <summary>
    /// 外部受入エラー情報
    /// </summary>
    public class AcceptErrorInfo
    {
        #region コンストラクタ
        public AcceptErrorInfo() { }

        public AcceptErrorInfo(JObject aJObject)
        {
            this._text = aJObject.GetValue("text").ToObject<string>();
            this._errorsCount = aJObject.GetValue("errorsCount").ToObject<int>();
        }
        #endregion

        #region 内部変数
        private string _text = "";

        private int _errorsCount = 0;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// エラー内容
        /// </summary>
        public string Text
        {
            get { return _text; }
            set { _text = value; }
        }

        /// <summary>
        /// エラー数
        /// </summary>
        public int ErrorsCount
        {
            get { return _errorsCount; }
            set { _errorsCount = value; }
        }
        #endregion プロパティ
    }
}
