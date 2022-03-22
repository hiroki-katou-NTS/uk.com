using System;

namespace WSISmile.Business.Link.Parameter.Cookie
{
    /// <summary>
    /// クッキー情報
    /// </summary>
    public class CookieInfo
    {
        #region コンストラクタ
        public CookieInfo() { }

        /// <summary>
        /// クッキー情報作成
        /// </summary>
        /// <param name="name">クッキー名</param>
        /// <param name="value">クッキー値</param>
        /// <param name="path">クッキーパス</param>
        /// <param name="domain">クッキードメイン</param>
        public CookieInfo(string name, string value, string path, string domain)
        {
            this._name = name;
            this._value = value;
            this._path = path;
            this._domain = domain;
        }
        #endregion コンストラクタ

        #region 内部変数
        private string _name = "";

        private string _value = "";

        private string _path = "";

        private string _domain = "";
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// クッキー名
        /// </summary>
        public string Name
        {
            get { return _name; }
            set { _name = value; }
        }

        /// <summary>
        /// クッキー値
        /// </summary>
        public string Value
        {
            get { return _value; }
            set { _value = value; }
        }

        /// <summary>
        /// クッキーパス
        /// </summary>
        public string Path
        {
            get { return _path; }
            set { _path = value; }
        }

        /// <summary>
        /// クッキードメイン
        /// </summary>
        public string Domain
        {
            get { return _domain; }
            set { _domain = value; }
        }
        #endregion プロパティ
    }
}
