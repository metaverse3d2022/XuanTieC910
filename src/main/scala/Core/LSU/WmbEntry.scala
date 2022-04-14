package Core.LSU
import chisel3._
import chisel3.util._

class InstInfo extends Bundle{
  val sync_fence     = Bool()
  val atomic         = Bool()
  val icc            = Bool()
  val inst_flush     = Bool()
  val inst_is_dcache = Bool()
  val inst_type      = UInt(2.W)
  val inst_size      = UInt(3.W)
  val inst_mode      = UInt(2.W)
  val fence_mode     = UInt(4.W)
  val iid            = UInt(7.W)
  val priv_mode      = UInt(2.W)
  val page_share     = Bool()
  val page_so        = Bool()
  val page_ca        = Bool()
  val page_wa        = Bool()
  val page_buf       = Bool()
  val page_sec       = Bool()
  val merge_en       = Bool()
  val addr           = UInt(LSUConfig.PA_WIDTH.W)
  val spec_fail      = Bool()
  val bkpta_data     = Bool()
  val bkptb_data     = Bool()
  val vstart_vld     = Bool()
}

class DcacheInfo extends Bundle{
  val valid = Bool()
  val share = Bool()
  val dirty = Bool()
  val way   = Bool()
}

class WmbEntryInput extends Bundle{
  val fromBiu = new Bundle{
    val b_id = UInt(5.W)
    val b_vld = Bool()
    val r_id = UInt(5.W)
    val r_vld = Bool()
  }
  val fromBusArb = new Bundle{
    val aw_grnt = Bool()
    val w_grnt = Bool()
  }
  val fromCp0 = new Bundle{
    val lsu_icg_en = Bool()
    val yy_clk_en = Bool()
  }
  val fromDCache = new Bundle{
    val dirty_din = UInt(7.W)
    val dirty_gwen = Bool()
    val dirty_wen = UInt(7.W)
    val idx = UInt(9.W)
    val snq_st_sel = Bool()
    val tag_din = UInt(52.W)
    val tag_gwen = Bool()
    val tag_wen = UInt(2.W)
  }
  val fromLoadDC = new Bundle{
    val addr0 = UInt(40.W)
    val addr1_11to4 = UInt(8.W)
    val bytes_vld = UInt(16.W)
    val chk_atomic_inst_vld = Bool()
    val chk_ld_inst_vld = Bool()
  }
  val fromLm = new Bundle{
    val state_is_ex_wait_lock = Bool()
    val state_is_idle = Bool()
  }
  val fromPad = new Bundle{
    val yy_icg_scan_en = Bool()
  }
  val pfu_biu_req_addr = UInt(40.W)
  val pw_merge_stall = Bool()
  val rb_biu_req_addr = UInt(40.W)
  val rb_biu_req_unmask = Bool()
  val rtu_lsu_async_flush = Bool()
  val fromSnq = new Bundle{
    val can_create_snq_uncheck = Bool()
    val create_addr = UInt(40.W)
  }
  val fromSQ = new Bundle{
    val pop_addr = UInt(40.W)
    val pop_priv_mode = UInt(2.W)
    val wmb_merge_req = Bool()
    val wmb_merge_stall_req = Bool()
    val update_dcache_dirty = Bool()
    val update_dcache_share = Bool()
    val update_dcache_valid = Bool()
    val update_dcache_way = Bool()
  }
  val fromVb = new Bundle{
    val empty = Bool()
    val rcl_done = Bool()
  }
  val wmb_b_resp_exokay = Bool()
  val wmb_biu_ar_id = UInt(5.W)
  val wmb_biu_aw_id = UInt(5.W)
  val wmb_biu_write_en = Bool()

  val fromWmbCe = new FromWmbCe

  val wmb_create_ptr_next1 = Bool()
  val wmb_create_vb_success = Bool()
  val wmb_data_ptr = Bool()
  val wmb_dcache_arb_req_unmask = Bool()
  val wmb_dcache_inst_write_req_hit_idx = Bool()


  val wmb_ce_create_vld = Bool()
  val wmb_ce_update_same_dcache_line = Bool()
  val wmb_ce_update_same_dcache_line_ptr = Vec(LSUConfig.WMB_ENTRY, Bool())
  val wmb_ce_last_addr_plus = Bool()
  val wmb_ce_last_addr_sub = Bool()
  val WmbEntry = new Bundle{
    val create_data_vld = Bool()
    val create_dp_vld = Bool()
    val create_gateclk_en = Bool()
    val create_vld = Bool()
    val mem_set_write_gateclk_en = Bool()
    val mem_set_write_grnt = Bool()
    val merge_data_vld = Bool()
    val merge_data_wait_not_vld_req = Bool()
    val next_nc_bypass = Bool()
    val next_so_bypass = Bool()
    val w_last_set = Bool()
    val wb_cmplt_grnt = Bool()
    val wb_data_grnt = Bool()
  }
  val WmbRead = new Bundle{
    val ptr_read_req_grnt = Bool()
    val ptr_shift_imme_grnt = Bool()
    val ptr = Bool()
  }
  val wmb_same_line_resp_ready = UInt(8.W)
  val wmb_wakeup_queue_not_empty = Bool()
  val WmbWrite = new Bundle{
    val biu_dcache_line = Bool()
    val dcache_success = Bool()
    val ptr_shift_imme_grnt = Bool()
    val ptr = Bool()
  }
}

class WmbEntryOutput extends Bundle{
  val addr = UInt(40.W)
  val ar_pending = Bool()
  val atomic_and_vld = Bool()
  val atomic = Bool()
  val aw_pending = Bool()
  val biu_id = UInt(5.W)
  val bkpta_data = Bool()
  val bkptb_data = Bool()
  val bytes_vld = UInt(16.W)
  val cancel_acc_req = Bool()
  val data_biu_req = Bool()
  val data_ptr_after_write_shift_imme = Bool()
  val data_ptr_with_write_shift_imme = Bool()
  val data_req_wns = Bool()
  val data_req = Bool()
  val data = UInt(128.W)
  val dcache_inst = Bool()
  val dcache_way = Bool()
  val depd = Bool()
  val discard_req = Bool()
  val fwd_bytes_vld = UInt(16.W)
  val fwd_data_pe_gateclk_en = Bool()
  val fwd_data_pe_req = Bool()
  val fwd_req = Bool()
  val hit_sq_pop_dcache_line = Bool()
  val icc_and_vld = Bool()
  val icc = Bool()
  val iid = UInt(7.W)
  val inst_flush = Bool()
  val inst_is_dcache = Bool()
  val inst_mode = UInt(2.W)
  val inst_size = UInt(3.W)
  val inst_type = UInt(2.W)
  val last_addr_plus = Bool()
  val last_addr_sub = Bool()
  val merge_data_addr_hit = Bool()
  val merge_data_stall = Bool()
  val no_op = Bool()
  val page_buf = Bool()
  val page_ca = Bool()
  val page_sec = Bool()
  val page_share = Bool()
  val page_so = Bool()
  val page_wa = Bool()
  val pfu_biu_req_hit_idx = Bool()
  val pop_vld = Bool()
  val preg = UInt(7.W)
  val priv_mode = UInt(2.W)
  val rb_biu_req_hit_idx = Bool()
  val read_dp_req = Bool()
  val read_ptr_chk_idx_shift_imme = Bool()
  val read_ptr_unconditional_shift_imme = Bool()
  val read_req = Bool()
  val read_resp_ready = Bool()
  val ready_to_dcache_line = Bool()
  val sc_wb_success = Bool()
  val snq_depd_remove = Bool()
  val snq_depd = Bool()
  val spec_fail = Bool()
  val sync_fence_biu_req_success = Bool()
  val sync_fence_inst = Bool()
  val sync_fence = Bool()
  val vld = Bool()
  val vstart_vld = Bool()
  val w_last = Bool()
  val w_pending = Bool()
  val wb_cmplt_req = Bool()
  val wb_data_req = Bool()
  val write_biu_dp_req = Bool()
  val write_biu_req = Bool()
  val write_dcache_req = Bool()
  val write_imme_bypass = Bool()
  val write_imme = Bool()
  val write_ptr_chk_idx_shift_imme = Bool()
  val write_ptr_unconditional_shift_imme = Bool()
  val write_req = Bool()
  val write_stall = Bool()
  val write_vb_req = Bool()
}

class WmbEntryIO extends Bundle{
  val in = Input(new WmbEntryInput)
  val out = Output(new WmbEntryOutput)
}

class WmbEntry extends Module {
  val io = IO(new WmbEntryIO)

  //Reg
  val wmb_entry_vld = RegInit(false.B)
  val inst_info = RegInit(0.U.asTypeOf(new InstInfo))
  val dcache_info = RegInit(0.U.asTypeOf(new DcacheInfo))
  val wmb_entry_write_stall = RegInit(false.B)
  val wmb_entry_bytes_vld_full = RegInit(false.B)
  val wmb_entry_bytes_vld = RegInit(0.U(16.W))
  val wmb_entry_data = RegInit(VecInit(Seq.fill(4)(0.U(32.W))))

  val wmb_entry_read_req_success = RegInit(false.B)
  val wmb_entry_read_resp = RegInit(false.B)
  val wmb_entry_write_req_success = RegInit(false.B)
  val wmb_entry_write_resp = RegInit(false.B)
  val wmb_entry_data_req_success = RegInit(false.B)

  val wmb_entry_biu_id = RegInit(0.U(5.W))
  val wmb_entry_biu_r_id_vld = RegInit(false.B)
  val wmb_entry_biu_b_id_vld = RegInit(false.B)
  val wmb_entry_w_last = RegInit(false.B)
  val wmb_entry_mem_set_req = RegInit(false.B)
  val wmb_entry_wb_cmplt_success = RegInit(false.B)
  val wmb_entry_wb_data_success = RegInit(false.B)

  val wmb_entry_same_dcache_line = RegInit(false.B)
  val wmb_entry_same_dcache_line_ptr = RegInit(VecInit(Seq.fill(LSUConfig.WMB_ENTRY)(false.B)))
  val wmb_entry_write_imme = RegInit(false.B)
  val wmb_entry_depd = RegInit(false.B)
  val wmb_entry_sc_wb_vld = RegInit(false.B)
  val wmb_entry_sc_wb_success = RegInit(false.B)
  val wmb_entry_last_addr_plus = RegInit(false.B)
  val wmb_entry_last_addr_sub = RegInit(false.B)
  //Wire
  val wmb_entry_data_clk_en = Wire(UInt(4.W))
  val wmb_entry_bytes_vld_clk_en = Wire(Bool())

  val wmb_entry_read_req = Wire(Bool())
  val wmb_entry_write_biu_req = Wire(Bool())
  val wmb_entry_mem_set_write_gateclk_en = Wire(Bool())
  val wmb_entry_r_id_hit = Wire(Bool())
  val wmb_entry_b_id_hit = Wire(Bool())
  val wmb_entry_pop_vld = Wire(Bool())
  val wmb_entry_dcache_update_vld = Wire(Bool())
  val update_dcache_info = Wire(new DcacheInfo)
  val wmb_entry_merge_data_grnt = Wire(Bool())
  val wmb_entry_bytes_vld_full_and = Wire(Bool())
  val wmb_entry_bytes_vld_and = Wire(UInt(16.W))
  val wmb_entry_create_data = Wire(UInt(4.W))
  val wmb_entry_merge_data = Wire(UInt(4.W))
  val wmb_entry_data_next = Wire(Vec(16, UInt(8.W)))

  val wmb_entry_read_req_success_set = Wire(Bool())
  val wmb_entry_read_resp_set = Wire(Bool())
  val wmb_entry_write_req_success_set = Wire(Bool())
  val wmb_entry_write_resp_set = Wire(Bool())
  val wmb_entry_data_req_success_set = Wire(Bool())

  val wmb_entry_mem_set_write_grnt = Wire(Bool())
  val wmb_entry_biu_r_id_vld_set = Wire(Bool())
  val wmb_entry_r_resp_vld = Wire(Bool())
  val wmb_entry_biu_b_id_vld_set = Wire(Bool())
  val wmb_entry_b_resp_vld = Wire(Bool())
  val wmb_entry_w_last_set = Wire(Bool())
  val wmb_entry_wb_cmplt_grnt = Wire(Bool())
  val wmb_entry_wb_data_grnt = Wire(Bool())

  val wmb_entry_same_dcache_line_clr = Wire(Bool())
  val wmb_entry_write_imme_set = Wire(Bool())
  val wmb_entry_discard_req = Wire(Bool())
  val wmb_entry_fwd_req = Wire(Bool())
  val wmb_entry_sc_wb_set = Wire(Bool())
  val wmb_entry_sc_wb_success_set = Wire(Bool())

  val wmb_entry_st_inst = Wire(Bool())
  val wmb_entry_wo_st_write_biu_req = Wire(Bool())
  val wmb_entry_data_req = Wire(Bool())
  val wmb_dcache_req_ptr = Wire(Bool())

  //==========================================================
  //                 Instance of Gated Cell
  //==========================================================
  //----------entry gateclk---------------
  val wmb_entry_clk_en = wmb_entry_vld || io.in.WmbEntry.create_gateclk_en

  //-----------create gateclk-------------
  val wmb_entry_create_clk_en = io.in.WmbEntry.create_gateclk_en

  //----------data gateclk----------------

  //biu_id_gate_clk
  val wmb_entry_biu_id_clk_en = io.in.WmbEntry.create_gateclk_en || wmb_entry_read_req || wmb_entry_write_biu_req ||
    wmb_entry_mem_set_write_gateclk_en || wmb_entry_r_id_hit || wmb_entry_b_id_hit

  //==========================================================
  //                 Register
  //==========================================================
  //+-----------+
  //| entry_vld |
  //+-----------+
  when(wmb_entry_pop_vld){
    wmb_entry_vld := false.B
  }.elsewhen(io.in.WmbEntry.create_vld){
    wmb_entry_vld := true.B
  }

  //+-------------------------+
  //| instruction information |
  //+-------------------------+
  when(io.in.WmbEntry.create_dp_vld){
    inst_info.sync_fence     :=  io.in.fromWmbCe.sync_fence
    inst_info.atomic         :=  io.in.fromWmbCe.atomic
    inst_info.icc            :=  io.in.fromWmbCe.icc
    inst_info.inst_flush     :=  io.in.fromWmbCe.inst_flush
    inst_info.inst_is_dcache :=  io.in.fromWmbCe.dcache_inst
    inst_info.inst_type      :=  io.in.fromWmbCe.inst_type
    inst_info.inst_size      :=  io.in.fromWmbCe.inst_size
    inst_info.inst_mode      :=  io.in.fromWmbCe.inst_mode
    inst_info.fence_mode     :=  io.in.fromWmbCe.fence_mode
    inst_info.iid            :=  io.in.fromWmbCe.iid
    inst_info.priv_mode      :=  io.in.fromWmbCe.priv_mode
    inst_info.page_share     :=  io.in.fromWmbCe.page_share
    inst_info.page_so        :=  io.in.fromWmbCe.page_so
    inst_info.page_ca        :=  io.in.fromWmbCe.page_ca
    inst_info.page_wa        :=  io.in.fromWmbCe.page_wa
    inst_info.page_buf       :=  io.in.fromWmbCe.page_buf
    inst_info.page_sec       :=  io.in.fromWmbCe.page_sec
    inst_info.merge_en       :=  io.in.fromWmbCe.merge_en
    inst_info.addr           :=  io.in.fromWmbCe.addr
    inst_info.spec_fail      :=  io.in.fromWmbCe.spec_fail
    inst_info.bkpta_data     :=  io.in.fromWmbCe.bkpta_data
    inst_info.bkptb_data     :=  io.in.fromWmbCe.bkptb_data
    inst_info.vstart_vld     :=  io.in.fromWmbCe.vstart_vld
  }

  //+-------------------+
  //| cache_information |
  //+-------------------+
  when(io.in.WmbEntry.create_dp_vld){
    dcache_info.valid := io.in.fromSQ.update_dcache_dirty
    dcache_info.share := io.in.fromSQ.update_dcache_share
    dcache_info.dirty := io.in.fromSQ.update_dcache_valid
    dcache_info.way   := io.in.fromSQ.update_dcache_way
  }.elsewhen(wmb_entry_dcache_update_vld){
    dcache_info := update_dcache_info
  }

  //+--------------------+
  //| already merge grnt |
  //+--------------------+
  when(wmb_entry_merge_data_grnt && io.in.fromSQ.wmb_merge_req && io.in.wmb_ce_create_vld){
    wmb_entry_write_stall := true.B
  }.otherwise{
    wmb_entry_write_stall := false.B
  }

  //+--------------------+
  //| data and bytes_vld |
  //+--------------------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_bytes_vld_full := io.in.fromWmbCe.bytes_vld_full
    wmb_entry_bytes_vld      := io.in.fromWmbCe.bytes_vld
  }.elsewhen(io.in.WmbEntry.merge_data_vld){
    wmb_entry_bytes_vld_full :=  wmb_entry_bytes_vld_full_and
    wmb_entry_bytes_vld      :=  wmb_entry_bytes_vld_and
  }

  for(i <- 0 until 4){
    when(wmb_entry_create_data(i) || wmb_entry_merge_data(i)){
      wmb_entry_data(i) := wmb_entry_data_next.asUInt(32*i+31,32*i)
    }
  }

  //------------------success/resp signal---------------------
  //+------------------+
  //| read_req_success |
  //+------------------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_read_req_success := false.B
  }.elsewhen(wmb_entry_read_req_success_set){
    wmb_entry_read_req_success := true.B
  }

  //+-----------+
  //| read_resp |
  //+-----------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_read_resp := false.B
  }.elsewhen(wmb_entry_read_resp_set){
    wmb_entry_read_resp := true.B
  }

  //+-------------------+
  //| write_req_success |
  //+-------------------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_write_req_success := false.B
  }.elsewhen(wmb_entry_write_req_success_set){
    wmb_entry_write_req_success := true.B
  }

  //+-----------+
  //| write_resp |
  //+-----------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_write_resp := false.B
  }.elsewhen(wmb_entry_write_resp_set){
    wmb_entry_write_resp := true.B
  }

  //+------------------+
  //| data_req_success |
  //+------------------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_data_req_success := false.B
  }.elsewhen(wmb_entry_data_req_success_set){
    wmb_entry_data_req_success := true.B
  }

  //----------------------biu id signal-----------------------
  //+--------+
  //| biu_id |
  //+--------+
  when(wmb_entry_read_req){
    wmb_entry_biu_id := io.in.wmb_biu_ar_id
  }.elsewhen(wmb_entry_write_biu_req || wmb_entry_mem_set_write_grnt){
    wmb_entry_biu_id := io.in.wmb_biu_aw_id
  }

  //+------------+
  //| biu_id_vld |
  //+------------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_biu_r_id_vld := false.B
  }.elsewhen(wmb_entry_biu_r_id_vld_set){
    wmb_entry_biu_r_id_vld := true.B
  }.elsewhen(wmb_entry_r_resp_vld){
    wmb_entry_biu_r_id_vld := false.B
  }

  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_biu_b_id_vld := false.B
  }.elsewhen(wmb_entry_biu_b_id_vld_set){
    wmb_entry_biu_b_id_vld := true.B
  }.elsewhen(wmb_entry_b_resp_vld){
    wmb_entry_biu_b_id_vld := false.B
  }

  //+--------+
  //| w_last |
  //+--------+
  when(wmb_entry_write_biu_req || wmb_entry_mem_set_write_grnt){
    wmb_entry_w_last := wmb_entry_w_last_set
  }

  //+-------------+
  //| mem_set_req |
  //+-------------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_mem_set_req := false.B
  }.elsewhen(wmb_entry_mem_set_write_grnt){
    wmb_entry_mem_set_req := io.in.WmbWrite.biu_dcache_line
  }

  //-------------cmplt/data req success signal----------------
  //+------------------+
  //| wb_cmplt_success |
  //+------------------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_wb_cmplt_success := io.in.fromWmbCe.wb_cmplt_success
  }.elsewhen(wmb_entry_wb_cmplt_grnt || io.in.rtu_lsu_async_flush){
    wmb_entry_wb_cmplt_success := true.B
  }

  //+-----------------+
  //| wb_data_success |
  //+-----------------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_wb_data_success := io.in.fromWmbCe.wb_data_success
  }.elsewhen(wmb_entry_wb_data_grnt || io.in.rtu_lsu_async_flush){
    wmb_entry_wb_data_success := true.B
  }

  //------------same cache line write imme and depd-----------
  //+-----------------+
  //| same_dcache_line |
  //+-----------------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_same_dcache_line := io.in.wmb_ce_update_same_dcache_line
  }.elsewhen(wmb_entry_same_dcache_line_clr){
    wmb_entry_same_dcache_line := false.B
  }

  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_same_dcache_line_ptr := io.in.wmb_ce_update_same_dcache_line_ptr
  }

  //+------------+
  //| write_imme |
  //+------------+
  //if write req grnt, clear write imme
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_write_imme := io.in.fromWmbCe.write_imme
  }.elsewhen(wmb_entry_write_req_success_set){
    wmb_entry_write_imme := false.B
  }.elsewhen(wmb_entry_write_imme_set){
    wmb_entry_write_imme := true.B
  }

  //+------+
  //| depd |
  //+------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_depd := false.B
  }.elsewhen(wmb_entry_discard_req || wmb_entry_fwd_req){
    wmb_entry_depd := true.B
  }

  //----------------------stex signal-------------------------
  //+-----------+---------------+
  //| sc_wb_vld | sc_wb_success |
  //+-----------+---------------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_sc_wb_vld := io.in.fromWmbCe.sc_wb_vld
    wmb_entry_sc_wb_success := false.B
  }.elsewhen(wmb_entry_sc_wb_set){
    wmb_entry_sc_wb_vld := true.B
    wmb_entry_sc_wb_success := wmb_entry_sc_wb_success_set
  }

  //----------------------write burst judge signal-------------------------
  //+-----------+----------+
  //| addr_plus | addr_sub |
  //+-----------+----------+
  when(io.in.WmbEntry.create_dp_vld){
    wmb_entry_last_addr_plus := io.in.wmb_ce_last_addr_plus
    wmb_entry_last_addr_sub := io.in.wmb_ce_last_addr_sub
  }

  //==========================================================
  //                  Create/merge signal
  //==========================================================
  //wmb_entry_hit_sq_pop_cache_line is used for same_dcache_line
  val wmb_entry_hit_sq_pop_addr_tto6 = inst_info.addr(LSUConfig.PA_WIDTH-1,6) === io.in.fromSQ.pop_addr(LSUConfig.PA_WIDTH-1,6)
  val wmb_entry_hit_sq_pop_addr_5to4 = inst_info.addr(5,4) === io.in.fromSQ.pop_addr(5,4)
  val wmb_entry_hit_sq_pop_addr_tto4   = wmb_entry_hit_sq_pop_addr_tto6 && wmb_entry_hit_sq_pop_addr_5to4

  val wmb_entry_hit_sq_pop_dcache_line =  wmb_entry_hit_sq_pop_addr_tto6 && wmb_entry_st_inst && wmb_entry_vld

  //if supv mode or page info is not hit, then set write_imme and donot grnt
  //signal to sq
  val wmb_entry_merge_data_addr_hit = wmb_entry_hit_sq_pop_addr_tto4 && inst_info.merge_en && wmb_entry_vld

  val wmb_entry_merge_data_permit = (inst_info.priv_mode === io.in.fromSQ.pop_priv_mode) &&
    !(wmb_entry_wo_st_write_biu_req || wmb_entry_data_req || wmb_dcache_req_ptr && (io.in.pw_merge_stall || io.in.wmb_dcache_arb_req_unmask)) &&
    !wmb_entry_write_req_success && !wmb_entry_data_req_success

  val wmb_entry_merge_data_stall = wmb_entry_merge_data_addr_hit && !wmb_entry_merge_data_permit

  wmb_entry_merge_data_grnt := wmb_entry_merge_data_addr_hit && wmb_entry_merge_data_permit

  val wmb_entry_merge_data_write_imme_set = wmb_entry_merge_data_addr_hit && io.in.fromSQ.wmb_merge_stall_req

  wmb_entry_merge_data := Mux(io.in.WmbEntry.merge_data_vld, io.in.fromWmbCe.data_vld, 0.U(4.W))

  wmb_entry_create_data := Mux(io.in.WmbEntry.create_data_vld, io.in.fromWmbCe.data_vld, 0.U(4.W))

  val wmb_entry_create_merge_data_gateclk_en = io.in.WmbEntry.create_gateclk_en || io.in.WmbEntry.merge_data_vld

  wmb_entry_data_clk_en := Mux(wmb_entry_create_merge_data_gateclk_en, io.in.fromWmbCe.data_vld, 0.U(4.W))

  wmb_entry_bytes_vld_clk_en := wmb_entry_create_merge_data_gateclk_en

  //------------------merge data------------------------------


}
