<template>
  <div class="kafs20a2">
    <KafS00AComponent v-if="!!kafS00AParams" v-bind:params="kafS00AParams" />
    <!-- A2_3 -->
    <div class="field-set">
      <!-- A2_3_1 -->
      <div class="card card-label">
        <div class="card-header uk-bg-accordion">
          <span>{{ "KAFS20_20" | i18n }}</span>
          <span class="badge badge-warning"></span>
        </div>
      </div>
      <!-- A2_3_2 -->
      <div class="accordion py-3">
        <div class="card">
          <div class="card-header">
            <button class="btn btn-link" type="button">
              <!-- tiêu đề của collapse -->
              <span>{{ settingItems.name }}</span>
            </button>
          </div>
          <div class="collapse">
            <div class="card-body">
              <!-- nội dung của collapse -->
              {{ settingItems.note }}
            </div>
          </div>
        </div>
      </div>
      <!-- A2_3_4 -->
      <div
        v-if="!validAll"
        class="card bg-danger top-alert uk-text-danger topError"
      >
        <button class="btn btn-link uk-text-danger">
          <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
          {{ "KAFS20_25" | i18n }}
        </button>
      </div>
    </div>
    <KafS00BComponent v-if="!!kafS00BParams" v-bind:params="kafS00BParams" />
    <!-- A2_6 -->
    <div class="field-set">
      <!-- A2_6_1 -->
      <div class="card card-label">
        <div class="card-header uk-bg-accordion">
          <span>{{ "KAFS20_21" | i18n }}</span>
          <span class="badge badge-warning"></span>
        </div>
      </div>
      <!-- A2_6_2 -->
      <div v-if="!!optionalItems">
        <div v-for="(item, index) in optionalItems" v-bind:key="index">
          <div class="accordion py-2 position-relative">
            <div class="card">
              <div class="card-header">
                <button class="btn btn-link" type="button">
                  {{ item.optionalItemDto.optionalItemName }}
                </button>
              </div>
              <div class="collapse">
                <div class="card-body">
                  <!-- nội dung của collapse -->
                  <span
                  v-if="condition">{{textConvert}}
                  </span>
                </div>
              </div>
            </div>
          </div>
          <!-- A2_6_4_1 -->
          <div class="position-relative mt-2">
            <nts-time-editor
              v-model="time"
              v-if="item.optionalItemDto.optionalItemAtr == 0"
              v-bind:show-title="false"
              time-input-type="time-with-day"
              v-bind:columns="{ input: 'col-10' }"
            />
            <nts-number-editor
              v-model="number"
              v-if="item.optionalItemDto.optionalItemAtr == 1 || item.optionalItemDto.optionalItemAtr == 2"
              v-bind:show-title="false"
              v-bind:columns="{ input: 'col-10' }"
            />
            <span class="position-absolute">{{
              item.optionalItemDto.unit
            }}</span>
          </div>
        </div>
      </div>
    </div>
    <KafS00CComponent v-if="!!kafS00CParams" v-bind:params="kafS00CParams" />
    <!-- A2_8_1 -->
    <button
      type="button"
      class="btn btn-primary btn-block"
      v-on:click="nextToStep3()"
    >
      {{ "KAFS20_30" | i18n }}
    </button>
    <!-- A2_8_2 -->
    <button
      type="button"
      class="btn btn-secondary btn-block"
      v-on:click="backToStep1()"
    >
      {{ "KAFS20_22" | i18n }}
    </button>
  </div>
</template>