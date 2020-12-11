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
        v-if="!isValidateAll"
        class="card bg-danger top-alert uk-text-danger topError"
      >
        <button class="btn btn-link uk-text-danger">
          <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
          {{ "KAFS20_25" | i18n }}
        </button>
      </div>
    </div>
    <KafS00BComponent 
    v-if="!!kafS00BParams" 
    v-bind:params="kafS00BParams" 
    @kaf000BChangePrePost="handleKaf00BChangePrePost"
    @kaf000BChangeDate="handleKaf00BChangeDate"
    />
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
      <div v-if="!!optionalItemApplication">
        <div v-for="(item, index) of optionalItemApplication" v-bind:key="index">
          <div class="accordion py-2 position-relative">
            <div class="card">
              <div class="card-header">
                <button class="btn btn-link" type="button">
                  {{ item.optionalItemName }}
                </button>
              </div>
              <div class="collapse">
                <div class="card-body">
                  <span>{{item.description}}</span><br>
                  <span v-if="item.lowerCheck || item.upperCheck || item.unit">
                    {{ 'KAF020_25' | i18n }}
                  </span>
                  <span v-if="item.lowerCheck || item.upperCheck">
                    {{ 'KAF020_26' | i18n}}
                  </span>
                  <span v-if="item.lowerCheck">
                    <span v-if="item.optionalItemAtr == 0 && item.timeLower != null">
                      {{ item.timeLower | timewd }}
                    </span>
                    <span v-if="item.optionalItemAtr == 1">
                      {{ item.numberLower }}
                    </span>
                    <span v-if="item.optionalItemAtr == 2">
                      {{ item.amountLower }}
                    </span>
                  </span>
                  <span v-if="item.lowerCheck || item.upperCheck">
                    {{'KAF020_27' | i18n}}
                  </span>
                  <span v-if="item.upperCheck">
                    <span v-if="item.optionalItemAtr == 0 && item.timeUpper != null">
                      {{ item.timeUpper | timewd }}
                    </span>
                    <span v-if="item.optionalItemAtr == 1">
                      {{ item.numberUpper }}
                    </span>
                    <span v-if="item.optionalItemAtr == 2">
                      {{ item.amountUpper }}
                    </span>
                  </span>
                  <span v-if="item.unit">
                    {{'KAF020_28' | i18n}}
                    <span>
                      {{item.inputUnitOfTimeItem}}
                    </span>
                    <span>
                      {{item.unit}}
                    </span>
                  </span>
                  <span v-if="item.lowerCheck || item.upperCheck || item.unit">
                    {{'KAF020_29' | i18n}}
                  </span>
                </div>
              </div>
            </div>
          </div>
          <!-- A2_6_4_1 -->
          <div class="position-relative mt-2">
            <nts-time-editor
              v-model="optionalItemApplication[index].time"
              v-if="item.optionalItemAtr == 0"
              v-bind:show-title="false"
              v-bind:record-id="index"
              time-input-type="time-with-day"
              v-bind:columns="{ input: 'col-10' }"
            />
            <nts-number-editor
              v-model="optionalItemApplication[index].number"
              v-if="item.optionalItemAtr == 1"
              v-bind:show-title="false"
              v-bind:record-id="index"
              v-bind:columns="{ input: 'col-10' }"
            />
            <nts-number-editor
              v-model="optionalItemApplication[index].amount"
              v-if="item.optionalItemAtr == 2"
              v-bind:show-title="false"
              v-bind:record-id="index"
              v-bind:columns="{ input: 'col-10' }"
            />
            <span class="position-absolute">{{ item.unit }}</span>
          </div>
        </div>
      </div>
    </div>
    <KafS00CComponent 
    v-if="!!kafS00CParams" 
    v-bind:params="kafS00CParams" 
    @kaf000CChangeAppReason="handleKaf00CChangeAppReason"
    @kaf000CChangeReasonCD="handleKaf00CChangeReasonCD"
    />
    <!-- A2_8_1 -->
    <button
      type="button"
      v-if="mode"
      class="btn btn-primary btn-block"
      v-on:click="nextToStep3()"
    >
      {{ "KAFS20_30" | i18n }}
    </button>
    <button
      type="button"
      v-else
      class="btn btn-primary btn-block"
      v-on:click="updateOptionalItem()"
    >
      {{ "KAFS20_31" | i18n }}
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