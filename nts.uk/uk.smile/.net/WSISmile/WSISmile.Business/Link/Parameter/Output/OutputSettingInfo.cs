using System;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// �O���o�͂̐ݒ���
    /// </summary>
    public class OutputSettingInfo
    {
        #region �R���X�g���N�^
        public OutputSettingInfo() { }

        /// <summary>
        /// GetOutputSetting�̃��X�|���X(JObject)����L���X�g����
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
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private string _conditionSetCd = "";

        private string _condName = "";

        private bool _conditionOutputName = false;

        private bool _itemOutputName = false;

        private Delimiter _delimiter = Delimiter.None;

        private StringFormat _stringFormat = StringFormat.None;

        private List<string> _itemCodeList = new List<string>();
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �O���o�͏����R�[�h
        /// </summary>
        public string ConditionSetCd
        {
            get { return _conditionSetCd; }
            set { _conditionSetCd = value; }
        }

        /// <summary>
        /// �O���o�͏�����
        /// </summary>
        public string ConditionSetName
        {
            get { return _condName; }
            set { _condName = value; }
        }

        /// <summary>
        /// �������o�͋敪
        /// </summary>
        public bool CondNameDisplay
        {
            get { return _conditionOutputName; }
            set { _conditionOutputName = value; }
        }

        /// <summary>
        /// ���ږ��o�͋敪
        /// </summary>
        public bool ItemNameDisplay
        {
            get { return _itemOutputName; }
            set { _itemOutputName = value; }
        }

        /// <summary>
        /// ��؂蕶��
        /// </summary>
        public Delimiter Delimiter
        {
            get { return _delimiter; }
            set { _delimiter = value; }
        }

        /// <summary>
        /// ������`��
        /// </summary>
        public StringFormat StringFormat
        {
            get { return _stringFormat; }
            set { _stringFormat = value; }
        }

        /// <summary>
        /// �o�͍��ڃR�[�hList
        /// </summary>
        public List<string> ItemCodeList
        {
            get { return _itemCodeList; }
            set { _itemCodeList = value; }
        }
        #endregion �v���p�e�B
    }
}
