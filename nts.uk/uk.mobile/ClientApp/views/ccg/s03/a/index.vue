<template>
  <!-- A4 絞り込み -->
  <div id="A4">
    <!-- A4_1 表示期間(ラベル) -->
    <label>{{ "CCGS03_6" | i18n }}</label>
    <div class="row">
      <!-- A4_2 表示期間 -->
      <div class="col-8 col-xs-9 col-sm-10 col-md-11 col-lg-11">
        <nts-date-range-input tabindex="1" name="CCGS03_6" :show-title="false"></nts-date-range-input>
      </div>
      <div class="col-4 col-xs-3 col-sm-2 col-md-1 col-lg-1">
        <!-- A4_3 検索 -->
        <button tabindex="2" class="btn btn-secondary no-wrap">{{ "CCGS03_7" | i18n }}</button>
      </div>
    </div>
    <!-- A5_0 記念日アコーディオン -->
    <div>
      <!-- A5 記念日アコーディオン -->
      <div v-for="(item, index) in anniversaries" :key="index">
        <div class="accordion">
          <div class="card">
            <div class="card-header uk-bg-schedule-focus">
              <i class="fas fa-search"></i>
              {{ item.anniversaryNotice.anniversaryTitle }}
            </div>
            <div class="collapse">
              <div class="card-body">
                <!-- A5_2 記念日内容 -->
                <nts-label :constraint="{ required: false }">
                  <span class="break-space"> {{ item.anniversaryNotice.notificationMessage }} </span>
                  <span>{{ 'CCG003_16' | i18n(`${item.anniversaryNotice.anniversary}`) }}</span>
                </nts-label>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- A6 メッセージアコーディオン -->
      <div v-for="(item, index) in msgNotices" :key="index">
        <div class="accordion">
          <div class="card">
            <div class="card-header uk-bg-schedule-focus">
              <i class="fas fa-search"></i>
              {{ item.message.notificationMessage }}
            </div>
            <div class="collapse">
              <div class="card-body">
                <nts-label :constraint="{ required: false }">
                  <span class="break-space" v-html="item.messageDisplay"></span>
                  <span class="block-5">{{ 'CCG003_8' | i18n(`${item.creator}`) }}</span>
                  <span class="block-5">{{ 'CCG003_9' | i18n(`${item.dateDisplay}`) }}</span>
                </nts-label>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>