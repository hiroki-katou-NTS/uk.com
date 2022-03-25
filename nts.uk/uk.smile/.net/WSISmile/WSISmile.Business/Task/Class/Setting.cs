using System;

using WSISmile.Business.Link.Parameter.Setting;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// Smile連携設定情報
    /// </summary>
    public class Setting
    {
        /// <summary>
        /// Smile連携-外部出力設定情報
        /// </summary>
        public SmileOutputSetting SmileOutputSetting = new SmileOutputSetting();

        /// <summary>
        /// Smile連携-外部受入設定情報
        /// </summary>
        public SmileAcceptSetting SmileAcceptSetting = new SmileAcceptSetting();
    }
}
