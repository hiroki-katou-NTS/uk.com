using System;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Setting
{
    /// <summary>
    /// Smile連携-外部受入設定情報
    /// </summary>
    public class SmileAcceptSetting
    {
        #region コンストラクタ
        public SmileAcceptSetting() { }

        public SmileAcceptSetting(JArray aJArray)
        {
            foreach (JObject aJObject in aJArray)
            {
                AcceptCategorySetting acceptCategorySetting = new AcceptCategorySetting(aJObject);
                this._categoriesSetting.Add(acceptCategorySetting);

            }
        }
        #endregion コンストラクタ

        #region プロパティ
        private List<AcceptCategorySetting> _categoriesSetting = new List<AcceptCategorySetting>();

        /// <summary>
        /// 外部受入-カテゴリ設定情報List
        /// </summary>
        public List<AcceptCategorySetting> CategoriesSetting
        {
            get { return _categoriesSetting; }
            set { _categoriesSetting = value; }
        }
        #endregion プロパティ

        #region Method
        /// <summary>
        /// 指定した受入カテゴリNo.の外部受入条件コードを取得
        /// </summary>
        /// <param name="cat">カテゴリNo.</param>
        /// <returns></returns>
        public string GetConditionSetCdByCategory(AcceptCategory cat)
        {
            #region 指定した受入カテゴリNo.の外部受入条件コードを取得 : GetConditionSetCdByCategory
            AcceptCategorySetting targetCategory = new AcceptCategorySetting();

            foreach (AcceptCategorySetting acceptCategorySetting in this._categoriesSetting)
            {
                if (acceptCategorySetting.Category == cat)
                {
                    targetCategory = acceptCategorySetting;
                }
            }

            // 有効性チェック
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
    /// 外部受入-カテゴリ設定情報
    /// </summary>
    public class AcceptCategorySetting
    {
        #region コンストラクタ
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
        #endregion コンストラクタ

        #region 内部変数
        private AcceptCategory _category = AcceptCategory.None;

        private bool _use = false;

        private string _conditionSetCd = "";

        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// カテゴリNo.
        /// </summary>
        public AcceptCategory Category
        {
            get { return _category; }
            set { _category = value; }
        }

        /// <summary>
        /// 利用区分
        /// </summary>
        public bool Use
        {
            get { return _use; }
            set { _use = value; }
        }

        /// <summary>
        /// 外部受入条件コード
        /// </summary>
        public string ConditionSetCd
        {
            get { return _conditionSetCd; }
            set { _conditionSetCd = value; }
        }
        #endregion プロパティ
    }
}
