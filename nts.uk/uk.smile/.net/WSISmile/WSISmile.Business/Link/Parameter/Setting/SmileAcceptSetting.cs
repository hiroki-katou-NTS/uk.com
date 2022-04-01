using System;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Setting
{
    /// <summary>
    /// Smile�A�g-�O������ݒ���
    /// </summary>
    public class SmileAcceptSetting
    {
        #region �R���X�g���N�^
        public SmileAcceptSetting() { }

        public SmileAcceptSetting(JArray aJArray)
        {
            foreach (JObject aJObject in aJArray)
            {
                AcceptCategorySetting acceptCategorySetting = new AcceptCategorySetting(aJObject);
                this._categoriesSetting.Add(acceptCategorySetting);

            }
        }
        #endregion �R���X�g���N�^

        #region �v���p�e�B
        private List<AcceptCategorySetting> _categoriesSetting = new List<AcceptCategorySetting>();

        /// <summary>
        /// �O�����-�J�e�S���ݒ���List
        /// </summary>
        public List<AcceptCategorySetting> CategoriesSetting
        {
            get { return _categoriesSetting; }
            set { _categoriesSetting = value; }
        }
        #endregion �v���p�e�B

        #region Method
        /// <summary>
        /// �w�肵������J�e�S��No.�̊O����������R�[�h���擾
        /// </summary>
        /// <param name="cat">�J�e�S��No.</param>
        /// <returns></returns>
        public string GetConditionSetCdByCategory(AcceptCategory cat)
        {
            #region �w�肵������J�e�S��No.�̊O����������R�[�h���擾 : GetConditionSetCdByCategory
            AcceptCategorySetting targetCategory = new AcceptCategorySetting();

            foreach (AcceptCategorySetting acceptCategorySetting in this._categoriesSetting)
            {
                if (acceptCategorySetting.Category == cat)
                {
                    targetCategory = acceptCategorySetting;
                }
            }

            // �L�����`�F�b�N
            if (!targetCategory.Use)
            {
                return string.Empty;
            }
            if (targetCategory.ConditionSetCd == null)
            {
                return string.Empty;
            }

            return targetCategory.ConditionSetCd;
            #endregion
        }
        #endregion Method
    }

    /// <summary>
    /// �O�����-�J�e�S���ݒ���
    /// </summary>
    public class AcceptCategorySetting
    {
        #region �R���X�g���N�^
        public AcceptCategorySetting() { }

        public AcceptCategorySetting(JObject aJObject)
        {
            if (aJObject["cooperationAcceptance"] != null)
            {
                this._category = (AcceptCategory)System.Enum.ToObject(typeof(AcceptCategory), aJObject.GetValue("cooperationAcceptance").ToObject<int>());
            }

            if (aJObject["cooperationAcceptanceClassification"] != null)
            {
                this._use = Convert.ToBoolean(aJObject.GetValue("cooperationAcceptanceClassification").ToObject<int>());
            }

            if (aJObject["cooperationAcceptanceConditions"] != null)
            {
                this._conditionSetCd = aJObject.GetValue("cooperationAcceptanceConditions").ToObject<string>();
            }
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private AcceptCategory _category = AcceptCategory.None;

        private bool _use = false;

        private string _conditionSetCd = "";

        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �J�e�S��No.
        /// </summary>
        public AcceptCategory Category
        {
            get { return _category; }
            set { _category = value; }
        }

        /// <summary>
        /// ���p�敪
        /// </summary>
        public bool Use
        {
            get { return _use; }
            set { _use = value; }
        }

        /// <summary>
        /// �O����������R�[�h
        /// </summary>
        public string ConditionSetCd
        {
            get { return _conditionSetCd; }
            set { _conditionSetCd = value; }
        }
        #endregion �v���p�e�B
    }
}
