
" init.vim
"
" SPDX-FileCopyrightText: 2023 Alexander Murphy <amsupernova@pm.me>
"
" SPDX-License-Identifier: Apache-2.0
"
" My vim configuration file.


" Settings ------------------

" Ignore capitalisation in searches etc.
set ignorecase

" Tabs are 4 spaces
set expandtab
set tabstop=4
set shiftwidth=4
set softtabstop=4

" Highlight column as formatting guide
set colorcolumn=110

" Should be enabled, but to be sure in older vim installations
syntax enable
filetype plugin indent on

" Show line numbers
set number


" Plugins -------------------

call plug#begin()

" Colorscheme & formatting
Plug 'romainl/Apprentice'
Plug 'NLKNguyen/papercolor-theme'
Plug 'luochen1990/rainbow'
Plug 'jiangmiao/auto-pairs'
Plug 'khaveesh/vim-fish-syntax'

" Handy status bar
Plug 'vim-airline/vim-airline'

" Auto tidying
Plug 'axelf4/vim-strip-trailing-whitespace'

" Clojure specific
Plug 'guns/vim-sexp'
Plug 'Olical/conjure'

" Awesome
Plug 'tpope/vim-repeat'

" Fast ack
Plug 'mileszs/ack.vim'

" Codebase analysis
Plug 'nvim-treesitter/nvim-treesitter'

call plug#end()


" Plugin settings -----------

" Enable rainbow parens
let g:rainbow_active = 1

" Colorscheme down here as it gets imported
set background=dark
colorscheme PaperColor


" Mappings ------------------
"
" Leader is Space key
nnoremap <Space> <Nop>
let g:mapleader = " "
let g:maplocalleader = " "

" Of course.
imap jj <Esc>

" Quicksave
map <C-s> :w<Enter>

" Quick exit from terminal insert
tmap <C-j> <C-\><C-n>

" Run visual selection in open terminal
map <Leader>r y<C-w>jpA<Enter><C-\><C-n><C-w>k

" Quick jump to e.o.l.
imap <C-f> <Esc>A

" Navigate window splits
nmap <C-h> <C-w>h
nmap <C-j> <C-w>j
nmap <C-k> <C-w>k
nmap <C-l> <C-w>l

" Open Python terminal in split window
nnoremap <Leader>P <Nop>
nmap <Leader>P <C-w>s<C-w>j:set nonumber<Enter>:term<Enter>Apython<Enter><C-\><C-n><C-w>k

" Open jshll in terminal
nnoremap <Leader>J <Nop>
nmap <Leader>J <C-w>s<C-w>j:set nonumber<Enter>:term<Enter>Ajshell<Enter><C-\><C-n><C-w>k
