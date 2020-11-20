<template>
  <div class="kdls35">
    <!-- A1_1 -->
    <div class="modal-header rounded-0 d-block p-0">
      <div class="uk-bg-teal p-2">
        <h4 class="modal-title text-white">
          <i class="fas fa-angle-double-left mr-1" v-on:click="back()"></i>
          <span>{{ "KDLS35_1" | i18n }}</span>
        </h4>
      </div>
      <!-- A1_2 -->
      <div class="uk-bg-headline py-3">
        <!-- A1_3 -->
        <span>{{ "KDLS35_2" | i18n }}}</span>
      </div>
      <!-- A2_1 -->
      <div class="p-2">
        <div class="row">
          <div class="col-6">
            <nts-label>{{ "KDLS35_3" | i18n }}</nts-label>
          </div>
          <!-- A2_2 -->
          <label class="col-6 pt-2">2020/06/01 ~ 2020/06/03</label>
        </div>
      </div>
    </div>
  </div>
</template>