using System;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// 外部出力の設定情報
    /// </summary>
    public class OutputSettingInfo
    {
        #region コンストラクタ
        public OutputSettingInfo() { }

        /// <summary>
        /// GetOutputSettingのレスポンス(JObject)からキャストする
        /// </summary>
        /// <param name="aJObject"></param>
        public OutputSettingInfo(JObject aJObject)
        {
            if (aJObject["condSet"] != null)
            {
                this._condName = aJObject.SelectToken("condSet").Value<string>("condName");

                this._conditionOutputName = Convert.ToBoolean(aJObject.SelectToken("condSet").Value<int>("conditionOutputName"));

                this._itemOutputName = Convert.ToBoolean(aJObject.SelectToken("condSet").Value<int>("itemOutputName"));

                this._delimiter = (Delimiter)System.Enum.ToObject(typeof(Delimiter), aJObject.SelectToken("condSet").Value<int>("delimiter"));

                this._stringFormat = (StringFormat)System.Enum.ToObject(typeof(StringFormat), aJObject.SelectToken("condSet").Value<int>("stringFormat"));

                this._itemCodeList = aJObject.SelectToken("condSet").Value<JArray>("itemCode").ToObject<List<string>>();
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private string _conditionSetCd = "";

        private string _condName = "";

        private bool _conditionOutputName = false;

        private bool _itemOutputName = false;

        private Delimiter _delimiter = Delimiter.None;

        private StringFormat _stringFormat = StringFormat.None;

        private List<string> _itemCodeList = new List<string>();
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 外部出力条件コード
        /// </summary>
        public string ConditionSetCd
        {
            get { return _conditionSetCd; }
            set { _conditionSetCd = value; }
        }

        /// <summary>
        /// 外部出力条件名
        /// </summary>
        public string ConditionSetName
        {
            get { return _condName; }
            set { _condName = value; }
        }

        /// <summary>
        /// 条件名出力区分
        /// </summary>
        public bool CondNameDisplay
        {
            get { return _conditionOutputName; }
            set { _conditionOutputName = value; }
        }

        /// <summary>
        /// 項目名出力区分
        /// </summary>
        public bool ItemNameDisplay
        {
            get { return _itemOutputName; }
            set { _itemOutputName = value; }
        }

        /// <summary>
        /// 区切り文字
        /// </summary>
        public Delimiter Delimiter
        {
            get { return _delimiter; }
            set { _delimiter = value; }
        }

        /// <summary>
        /// 文字列形式
        /// </summary>
        public StringFormat StringFormat
        {
            get { return _stringFormat; }
            set { _stringFormat = value; }
        }

        /// <summary>
        /// 出力項目コードList
        /// </summary>
        public List<string> ItemCodeList
        {
            get { return _itemCodeList; }
            set { _itemCodeList = value; }
        }
        #endregion プロパティ
    }
}
