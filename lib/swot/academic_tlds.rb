require 'set'

module Swot
  # These top-level domains are guaranteed to be academic institutions.
  ACADEMIC_TLDS = %w(
    ac.ae
    ac.at
    ac.bd
    ac.be
    ac.cr
    ac.cy
    ac.fj
    ac.id
    ac.il
    ac.in
    ac.ir
    ac.jp
    ac.ke
    ac.kr
    ac.ma
    ac.mu
    ac.mw
    ac.mz
    ac.nz
    ac.pa
    ac.pg
    ac.rs
    ac.ru
    ac.rw
    ac.th
    ac.tz
    ac.ug
    ac.uk
    ac.yu
    ac.za
    ac.zm
    ac.zw
    edu
    edu.af
    edu.al
    edu.ar
    edu.au
    edu.az
    edu.ba
    edu.bb
    edu.bd
    edu.bh
    edu.bi
    edu.bn
    edu.bo
    edu.br
    edu.bs
    edu.bt
    edu.bz
    edu.cn
    edu.co
    edu.cu
    edu.do
    edu.dz
    edu.ec
    edu.ee
    edu.eg
    edu.er
    edu.es
    edu.et
    edu.ge
    edu.gh
    edu.gr
    edu.gt
    edu.hk
    edu.hn
    edu.ht
    edu.iq
    edu.jm
    edu.jo
    edu.kg
    edu.kh
    edu.kn
    edu.kw
    edu.ky
    edu.kz
    edu.la
    edu.lb
    edu.lv
    edu.ly
    edu.mk
    edu.mm
    edu.mn
    edu.mo
    edu.mt
    edu.mx
    edu.my
    edu.ni
    edu.np
    edu.om
    edu.pa
    edu.pe
    edu.ph
    edu.pk
    edu.pl
    edu.pr
    edu.ps
    edu.pt
    edu.py
    edu.qa
    edu.rs
    edu.ru
    edu.sa
    edu.sd
    edu.sg
    edu.sv
    edu.sy
    edu.tr
    edu.tt
    edu.tw
    edu.ua
    edu.uy
    edu.ve
    edu.vn
    edu.ws
    edu.ye
    edu.zm
    vic.edu.au
  ).to_set.freeze
end
